package com.sms.pipe.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.di.vmHomeModules
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.model.AppletFilterContent
import com.sms.pipe.view.model.AppletFilterSender
import com.sms.pipe.view.model.AppletUi
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override val moduleList: List<Module> = listOf(vmHomeModules)

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel.getApplet()
    }

    override fun initViews() {
        binding.card.setOnLongClickListener {
            binding.card.isChecked = !binding.card.isChecked
            true
        }

        binding.cardEmpty.setOnClickListener {
        }
    }

    override fun initObservers() {
        fragmentViewModel.appletUi.observe(viewLifecycleOwner, ::setAppletUi)
    }

    private fun setAppletUi(applet: AppletUi) {
        binding.cardEmpty.visibility = View.GONE
        binding.card.visibility = View.VISIBLE
        binding.cardTitle.text = applet.appletName
        binding.cardDescription.text = getString(R.string.card_description, applet.channelName)
        applet.filters.forEach {
            when (it) {
                is AppletFilterSender -> binding.icSender.visibility = View.VISIBLE
                is AppletFilterContent -> binding.icSms.visibility = View.VISIBLE
            }
        }
        binding.rules.text =
            getString(R.string.card_rules, applet.filters.map { it.toString() }.toString())

    }
}