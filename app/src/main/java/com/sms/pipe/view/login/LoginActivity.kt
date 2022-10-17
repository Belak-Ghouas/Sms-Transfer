package com.sms.pipe.view.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.sms.pipe.databinding.LoginActivityBinding
import com.sms.pipe.di.domainLoginModules
import com.sms.pipe.di.vmLoginModule
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseActivity
import org.koin.core.module.Module

class LoginActivity: BaseActivity<LoginActivityViewModel, LoginActivityBinding>() {
    override val moduleList: List<Module>
        get() = listOf(vmLoginModule, domainLoginModules)


    override fun getViewModelClass() = LoginActivityViewModel::class
    override fun getViewBinding()= LoginActivityBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        didComeFromDeepLink()
    }

    private fun didComeFromDeepLink() {
        Log.d("LoginActivity ","We Come from deepLink")
    }

    override fun initViews() {
        binding.loginBtn.setOnClickListener{
            binding.inputEmail.text.toString().ifEmpty {
                binding.inputEmail.error ="Required field"
                return@setOnClickListener
            }
            binding.inputPassword.text.toString().ifEmpty {
                binding.inputPassword.error ="Required field"
                return@setOnClickListener
            }
           activityViewModel.login(binding.inputEmail.text.toString(),binding.inputPassword.text.toString())
        }

        binding.createAccount.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sms-pipe-web.web.app/register"))
            startActivity(browserIntent)
        }
    }

    override fun initObservers() {

        activityViewModel.isLogged.observe(this){
            loggedIn(it)
        }

        activityViewModel.loading.observe(this){
            showOverlayProgress(it)
        }
    }

    private fun loggedIn(isLoggedIn: Boolean) {
       if(isLoggedIn){
           val intent = Intent(this, MainActivity::class.java)
           startActivity(intent)
       }else{
            binding.inputEmail.text?.clear()
            binding.inputPassword.text?.clear()
       }
    }

    private fun showOverlayProgress(isActivate :Boolean){
        if(isActivate){
            binding.loader.isIndeterminate = true
            binding.loader.visibility =View.VISIBLE
            binding.overlay.visibility = View.VISIBLE
        }else{
            binding.loader.visibility =View.INVISIBLE
            binding.overlay.visibility = View.GONE
        }
    }

}