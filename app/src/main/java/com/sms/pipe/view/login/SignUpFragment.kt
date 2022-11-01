package com.sms.pipe.view.login

import android.util.Patterns
import android.view.View
import androidx.fragment.app.activityViewModels
import com.sms.pipe.databinding.SignUpFragmentBinding
import com.sms.pipe.utils.ErrorCodes
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.base.BaseFragmentViewModel
import org.koin.core.module.Module
import java.util.regex.Pattern

class SignUpFragment:BaseFragment<BaseFragmentViewModel,SignUpFragmentBinding>() {
    override val moduleList: List<Module>
        get() = listOf()

    private val loginViewModel  :LoginActivityViewModel by activityViewModels()

    override fun getViewBinding()= SignUpFragmentBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.loginBtn.setOnClickListener{
            binding.inputEmail.text.toString().ifEmpty {
                binding.inputEmail.error ="Required field"
                return@setOnClickListener
            }
            binding.inputPassword.text.toString().ifEmpty {
                binding.inputPassword.error ="Required field"
                binding.inputPassword
                return@setOnClickListener
            }

            binding.inputUsername.text.toString().ifEmpty {
                binding.inputPassword.error ="Required field"
                binding.inputPassword
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher((binding.inputEmail.text.toString())).matches()){
                binding.inputEmail.error ="Incorrect email address"
                return@setOnClickListener
            }


            loginViewModel.signUp(binding.inputEmail.text.toString(),binding.inputUsername.text.toString(),binding.inputPassword.text.toString())
        }
    }

    override fun initObservers() {
        loginViewModel.isRegistered.observe(viewLifecycleOwner,::handleSignUp)
    }

    private fun handleSignUp(isRegistered : Boolean){
        if(isRegistered){

        }else{
          binding.error.text = "Unknown error please try later"
        }
    }
}