package com.sms.pipe.view.login

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.sms.pipe.databinding.LoginFragmentBinding
import com.sms.pipe.utils.ErrorCodes
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.base.BaseFragmentViewModel
import org.koin.core.module.Module

class LoginFragment:BaseFragment<BaseFragmentViewModel,LoginFragmentBinding>() {

    private val loginViewModel  :LoginActivityViewModel by activityViewModels()

    override val moduleList: List<Module> = listOf()

    override fun getViewBinding() = LoginFragmentBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.loginBtn.setOnClickListener{

            hideKeyboardFrom(requireContext(),binding.root)
            binding.inputEmail.text.toString().ifEmpty {
                binding.inputEmail.error ="Required field"
                return@setOnClickListener
            }
            binding.inputPassword.text.toString().ifEmpty {
                binding.inputPassword.error ="Required field"
                binding.inputPassword
                return@setOnClickListener
            }
            loginViewModel.login(binding.inputEmail.text.toString(),binding.inputPassword.text.toString())
        }
    }

    override fun initObservers() {
        loginViewModel.error.observe(this,::handleError)
        loginViewModel.isLogged.observe(this,::loggedIn)
    }

    private fun loggedIn(isLoggedIn: Boolean) {
        if(isLoggedIn){

            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }else{
            binding.inputEmail.text?.clear()
            binding.inputPassword.text?.clear()
        }
    }
    private fun handleError(error: Int) {
        binding.error.visibility = View.VISIBLE
        when(error){
            ErrorCodes.USER_NOT_FOUND_ERROR ->{
                binding.error.text ="User not found , Create account ?"
            }
            ErrorCodes.INTERNAL_ERROR ->{
                binding.error.text ="Unknown error, please try after!"
            }
            ErrorCodes.WRONG_EMAIL_OR_PASSWORD_ERROR ->{
                binding.error.text ="Error on password or email address"
            }
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}