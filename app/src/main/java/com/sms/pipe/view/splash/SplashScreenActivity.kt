package com.sms.pipe.view.splash

import android.content.Intent
import android.os.Bundle
import com.sms.pipe.databinding.SplashScreenActivityBinding
import com.sms.pipe.di.vmSplashModule
import com.sms.pipe.view.login.LoginActivity
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseActivity
import org.koin.core.module.Module

class SplashScreenActivity:
    BaseActivity<SplashScreenActivityViewModel, SplashScreenActivityBinding>() {
    override val moduleList: List<Module>
        get() = listOf(vmSplashModule)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityViewModel.getLoggedUser()
    }

    override fun initViews() {
    }

    override fun initObservers() {
        activityViewModel.isLogged.observe(this){
            if(it){
                val i = Intent(this, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }else{
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        }
    }

    override fun getViewModelClass()= SplashScreenActivityViewModel::class

    override fun getViewBinding() = SplashScreenActivityBinding.inflate(layoutInflater)
}