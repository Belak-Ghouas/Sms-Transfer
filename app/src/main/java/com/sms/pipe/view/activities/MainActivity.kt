package com.sms.pipe.view.activities

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sms.pipe.R
import com.sms.pipe.databinding.ActivityMainBinding
import com.sms.pipe.framework.di.vmMainActivityModule
import com.sms.pipe.view.viewmodels.BaseActivityViewModel
import org.koin.core.module.Module

class MainActivity :BaseActivity<BaseActivityViewModel,ActivityMainBinding>() {
    override fun getViewModelClass() = BaseActivityViewModel::class
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
     //   setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override val moduleList: List<Module> = listOf(vmMainActivityModule)

    override fun initViews() {

    }

    override fun initObservers() {
    }


}