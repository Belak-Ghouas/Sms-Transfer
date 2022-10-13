package com.sms.pipe.view.profile

import android.content.Intent
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.databinding.FragmentProfileBinding
import com.sms.pipe.di.profileModules
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.login.LoginActivity
import org.koin.core.module.Module

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>() {

    override val moduleList: List<Module> = listOf(profileModules)

    override fun getViewBinding()= FragmentProfileBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.logoutCard.setOnClickListener {
            fragmentViewModel.logout()
        }

        binding.notification
    }

    override fun initObservers() {
     fragmentViewModel.loggedOut.observe(viewLifecycleOwner){
        val intent = Intent(requireContext(),LoginActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
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