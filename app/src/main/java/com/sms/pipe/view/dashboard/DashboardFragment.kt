package com.sms.pipe.view.dashboard

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import com.sms.pipe.databinding.FragmentDashboardBinding
import com.sms.pipe.di.vmDashboardModules
import com.sms.pipe.services.SMSReceiver
import com.sms.pipe.view.addApplet.CreateAppletActivity
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseFragment
import org.koin.core.module.Module


class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {


    override val moduleList: List<Module> = listOf(vmDashboardModules)

    override fun getViewBinding() =FragmentDashboardBinding.inflate(layoutInflater)

    private val activity : MainActivity by lazy {
        requireActivity() as MainActivity
    }
    override fun initViews() {
        binding.enable.setOnCheckedChangeListener { _, checked ->
            val flag = if(checked) PackageManager.COMPONENT_ENABLED_STATE_ENABLED else PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            val pm: PackageManager = requireActivity().packageManager
            val componentName = ComponentName(requireActivity(), SMSReceiver::class.java)
            pm.setComponentEnabledSetting(
                componentName, flag,
                PackageManager.DONT_KILL_APP)
        }

        binding.addNewRule.setOnClickListener {
            val intent = Intent(this.context, CreateAppletActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun initObservers() {
        fragmentViewModel.text.observe(viewLifecycleOwner) {
            binding.textDashboard.text = it
        }
    }

}