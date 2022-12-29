package com.sms.pipe.view.profile

import androidx.appcompat.widget.SwitchCompat
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.databinding.FragmentProfileBinding
import com.sms.pipe.di.profileModules
import com.sms.pipe.services.SMSReceiver
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseFragment
import org.koin.core.module.Module

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    private val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }
    override val moduleList: List<Module> = listOf(profileModules)

    override fun getViewBinding() = FragmentProfileBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.logoutCard.setOnClickListener {
            fragmentViewModel.logout()
        }

        binding.enableApplets.isChecked =
            mainActivity.isReceiverEnabled() && mainActivity.allPermissionsAreGranted().isEmpty()

        binding.enableApplets.setOnClickListener {

            enableServiceOnClick((it as SwitchCompat))
        }
    }

    private fun enableServiceOnClick(switch: SwitchCompat) {
        val checked = switch.isChecked
        if (checked) {
            if (mainActivity.allPermissionsAreGranted().isEmpty()) {
                mainActivity.enableService(SMSReceiver::class.java)
            } else {
                mainActivity.askUserPermissionWithDialog()
                switch.isChecked = false
            }
        } else {
            mainActivity.disableService(SMSReceiver::class.java)
        }


    }

    override fun initObservers() {
        fragmentViewModel.loggedOut.observe(viewLifecycleOwner) {
            mainActivity.logout()
        }

        fragmentViewModel.user.observe(viewLifecycleOwner) {
            bindUserUI(it)
        }
    }

    private fun bindUserUI(userModel: UserModel) {
        binding.email.text = userModel.email
        binding.username.text = userModel.username
    }
}