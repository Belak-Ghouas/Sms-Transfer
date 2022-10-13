package com.sms.pipe.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sms.pipe.R
import com.sms.pipe.databinding.ActivityMainBinding
import com.sms.pipe.di.vmMainActivityModule
import com.sms.pipe.view.base.BaseActivity
import com.sms.pipe.view.base.BaseActivityViewModel
import org.koin.core.module.Module

const val PERMISSION_REQUEST_CODE =99

class MainActivity : BaseActivity<BaseActivityViewModel, ActivityMainBinding>() {
    override fun getViewModelClass() = BaseActivityViewModel::class
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
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
            ActivityCompat.requestPermissions(this, listToAsk.toTypedArray(), PERMISSION_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                   AlertDialog.Builder(this)
                        .setMessage("getString(R.string.order_confirmation)")
                        .setPositiveButton("Ok") { _,_ -> }
                        .create()
                        .show()
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }


    fun navigateTo(fragmentId: Int) {
        findNavController(R.id.nav_host_fragment_activity_main).navigate(fragmentId)
    }

}