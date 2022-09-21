package com.sms.pipe.view.activities.ui.notifications

import com.sms.pipe.databinding.FragmentNotificationsBinding
import com.sms.pipe.framework.di.vmNotificationModules
import com.sms.pipe.view.fragments.BaseFragment
import org.koin.core.module.Module

class NotificationsFragment : BaseFragment<NotificationsViewModel,FragmentNotificationsBinding>() {

    override val moduleList: List<Module> = listOf(vmNotificationModules)

    override fun getViewBinding()= FragmentNotificationsBinding.inflate(layoutInflater)

    override fun initViews() {
    }

    override fun initObservers() {
     fragmentViewModel.text.observe(viewLifecycleOwner){
         binding.textNotifications.text=it
     }
    }
}