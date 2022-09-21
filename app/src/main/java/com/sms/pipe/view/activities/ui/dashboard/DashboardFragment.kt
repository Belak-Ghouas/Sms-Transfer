package com.sms.pipe.view.activities.ui.dashboard

import com.sms.pipe.databinding.FragmentDashboardBinding
import com.sms.pipe.framework.di.vmDashboardModules
import com.sms.pipe.view.fragments.BaseFragment
import org.koin.core.module.Module

class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {


    override val moduleList: List<Module> = listOf(vmDashboardModules)

    override fun getViewBinding() =FragmentDashboardBinding.inflate(layoutInflater)

    override fun initViews() {

    }

    override fun initObservers() {
        fragmentViewModel.text.observe(viewLifecycleOwner) {
            binding.textDashboard.text = it
        }
    }

}