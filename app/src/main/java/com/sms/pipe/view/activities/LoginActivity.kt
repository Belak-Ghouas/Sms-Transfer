package com.sms.pipe.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sms.pipe.databinding.LoginActivityBinding
import com.sms.pipe.framework.di.domainLoginModules
import com.sms.pipe.framework.di.vmLoginModule
import com.sms.pipe.view.viewmodels.LoginActivityViewModel
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
    }

    override fun initViews() {
        binding.loginBtn.setOnClickListener{
           activityViewModel.login(binding.inputEmail.text.toString(),binding.inputPassword.text.toString())
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
           val intent = Intent(this, ScrollingActivity::class.java)
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