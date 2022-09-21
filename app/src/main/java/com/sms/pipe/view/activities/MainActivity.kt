package com.sms.pipe.view.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    askPermission()
    }

    override fun initObservers() {
    }

    private fun askPermission(){

        val permissions = arrayOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission_group.SMS)
        val listToAsk = arrayListOf<String>()
        for (permission in permissions){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                listToAsk.add(permission)
            }
        }
        if(listToAsk.isNotEmpty()){
            ActivityCompat.requestPermissions(this, listToAsk.toTypedArray(), 1)
        }

    }

}