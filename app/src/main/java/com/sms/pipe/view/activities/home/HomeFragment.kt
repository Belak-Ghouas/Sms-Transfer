package com.sms.pipe.view.activities.home

import android.os.Bundle
import android.view.View
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.framework.di.vmHomeModules
import com.sms.pipe.view.fragments.BaseFragment
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {


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
    }

    override fun initObservers() {
        fragmentViewModel.text.observe(viewLifecycleOwner) {
         //   binding.titleHome.text=it
        }

        fragmentViewModel.appletUi.observe(viewLifecycleOwner){
            binding.card.visibility = View.VISIBLE
            binding.cardTitle.text = it.appletName
            binding.cardDescription.text = it.channelName
        }
    }
}