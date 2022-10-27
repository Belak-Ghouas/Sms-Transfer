package com.sms.pipe.view.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.di.homeModules
import com.sms.pipe.utils.ARG_SELECTED_APPLET
import com.sms.pipe.view.BottomSheetDeleteApplet
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

    override val moduleList: List<Module> = listOf(homeModules)

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

        binding.card.setOnClickListener {
            fragmentViewModel.appletUi.value?.id?.let {
                onAppletSelected(it)
            }
        }

        binding.cardEmpty.setOnClickListener {
        }
    }

    override fun initObservers() {
        fragmentViewModel.appletUi.observe(viewLifecycleOwner){applet->
            applet?.let {
                setAppletUi(it)
            }?:run{
                setDefaultCard()
            }

        }
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

    private fun setDefaultCard(){
        binding.cardEmpty.visibility = View.VISIBLE
        binding.card.visibility = View.GONE
    }

    private fun onAppletSelected(id:Long){

        val deleteAppletBottom = BottomSheetDeleteApplet()
        deleteAppletBottom.arguments = Bundle().apply {
            this.putLong(ARG_SELECTED_APPLET,id)
        }
        deleteAppletBottom.show(mActivity.supportFragmentManager,"DeleteApplet")
    }
}