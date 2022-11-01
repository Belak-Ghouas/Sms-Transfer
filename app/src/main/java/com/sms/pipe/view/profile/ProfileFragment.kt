package com.sms.pipe.view.profile

import android.content.Intent
import androidx.appcompat.widget.SwitchCompat
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.databinding.FragmentProfileBinding
import com.sms.pipe.di.profileModules
import com.sms.pipe.services.SMSReceiver
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.login.LoginActivity
import org.koin.core.module.Module

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    private val mainActivity :MainActivity by lazy {
        requireActivity() as MainActivity
    }
    override val moduleList: List<Module> = listOf(profileModules)

    override fun getViewBinding()= FragmentProfileBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.logoutCard.setOnClickListener {
            fragmentViewModel.logout()
        }

        binding.enableApplets.isChecked = mainActivity.isReceiverEnabled() && mainActivity.allPermissionsAreGranted().isEmpty()

        binding.enableApplets.setOnClickListener {

            enableServiceOnClick((it as SwitchCompat))
        }
    }

    private fun enableServiceOnClick(switch: SwitchCompat) {
        val checked = switch.isChecked
        if(checked){
            if(mainActivity.allPermissionsAreGranted().isEmpty()){
               mainActivity.enableService(SMSReceiver::class.java)
            }else{
                mainActivity.askPermission()
                switch.isChecked = false
            }
        }else{
           mainActivity.disableService(SMSReceiver::class.java)
        }


    }

    override fun initObservers() {
     fragmentViewModel.loggedOut.observe(viewLifecycleOwner){
        val intent = Intent(requireContext(),LoginActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         startActivity(intent)
         requireActivity().finish()
     }
        
        fragmentViewModel.user.observe(viewLifecycleOwner){
            bindUserUI(it)
        }
    }

    private fun bindUserUI(userModel: UserModel) {
        binding.email.text = userModel.email
        binding.username.text = userModel.username
    }
}