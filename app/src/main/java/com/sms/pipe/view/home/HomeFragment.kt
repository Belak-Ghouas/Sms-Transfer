package com.sms.pipe.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.di.vmHomeModules
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.model.AppletFilterContent
import com.sms.pipe.view.model.AppletFilterSender
import com.sms.pipe.view.model.AppletUi
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    override val moduleList: List<Module> = listOf(vmHomeModules)

    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel.getApplet()
    }
    override fun initViews() {
        binding.card.setOnLongClickListener {
            binding.card.isChecked = !binding.card.isChecked
            true
        }
        binding.addToSlack.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sms-pipe-web.web.app/login"))
            startActivity(browserIntent)
        }
    }

    override fun initObservers() {
               fragmentViewModel.appletUi.observe(viewLifecycleOwner,::setAppletUi)
    }

    private fun setAppletUi(applet: AppletUi) {
        binding.addToSlack.visibility = View.GONE
        binding.card.visibility = View.VISIBLE
        binding.cardTitle.text = applet.appletName
        binding.cardDescription.text = getString(R.string.card_description,applet.channelName)
        applet.filters.forEach{
            when(it){
                is AppletFilterSender -> binding.icSender.visibility = View.VISIBLE
                is AppletFilterContent->  binding.icSms.visibility = View.VISIBLE
            }
        }
        binding.rules.text = getString(R.string.card_rules,applet.filters.map { it.toString() }.toString())

    }
}