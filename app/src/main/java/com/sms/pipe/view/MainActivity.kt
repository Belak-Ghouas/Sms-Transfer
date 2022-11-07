package com.sms.pipe.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.sms.pipe.R
import com.sms.pipe.databinding.ActivityMainBinding
import com.sms.pipe.di.mainActivityModule
import com.sms.pipe.view.addApplet.CreateAppletActivity
import com.sms.pipe.view.base.BaseActivity
import org.koin.core.module.Module


const val PERMISSION_REQUEST_CODE = 99

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    private var needRefresh = false
    private var isFirstTime = true

    override fun getViewModelClass() = MainActivityViewModel::class
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override val moduleList: List<Module> = listOf(mainActivityModule)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        activityViewModel.getApplet()
        activityViewModel.refresh()
    }

    override fun onResume() {
        super.onResume()
        if (needRefresh) {
            needRefresh = false
            showSnackBarRefresh()
        }
    }


    private fun showSnackBarRefresh() {
        val snack = Snackbar.make(
            binding.root,
            "Need to refresh after adding you app to slack ",
            Snackbar.LENGTH_INDEFINITE
        )

        snack.setAction("Refresh") {
            activityViewModel.refresh()
            snack.dismiss()
        }.setActionTextColor(ContextCompat.getColor(this, R.color.bar_nav_color))
        snack.anchorView = binding.guideline
        snack.show()
    }

    override fun initObservers() {
     activityViewModel.refresh.observe(this){
         binding.swiperefresh.isRefreshing = it
     }
    }
    override fun initViews() {
        binding.navView.menu.forEach {
            if (it.itemId == R.id.navigation_dashboard) {
                it.setOnMenuItemClickListener {
                    createNewApplet()
                    true
                }
            }
        }
        binding.swiperefresh.setOnRefreshListener {
            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
           activityViewModel.refresh()
        }
    }


    fun createNewApplet() {
        if (activityViewModel.hasSlack.value == true) {
            if (allPermissionsAreGranted().isEmpty()) {
                openCreateAppletActivity()
            } else {
                askPermission()
            }
        } else {
            openAddToSlackBottomSheet()
        }
    }

    private fun openCreateAppletActivity() {
        val intent = Intent(this, CreateAppletActivity::class.java)
        startActivity(intent)
    }


    private fun openAddToSlackBottomSheet(){
        needRefresh = true
        val bottomSheet = BottomSheetAddToSlack()
        bottomSheet.show(supportFragmentManager, "BottomSheetAddToSlack")
    }




    fun askPermission() {
        allPermissionsAreGranted().apply {
            if (this.isNotEmpty()) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    this.toTypedArray(),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {

                        val icon = ContextCompat.getDrawable(this,R.drawable.ic_sms)
                        icon?.setTint(ContextCompat.getColor(this,R.color.bar_nav_color))
                        AlertDialog.Builder(this)
                            .setIcon(icon)
                            .setTitle(getString(R.string.sms_permission))
                            .setMessage(getString(R.string.need_permission))
                            .setPositiveButton("OK") { _, _ ->
                                if(isFirstTime){
                                    askPermission()
                                }else{
                                    val intent =
                                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    val uri = Uri.fromParts("package", packageName, null)
                                    intent.data = uri
                                    startActivity(intent)
                                }

                            }
                            .setNegativeButton("cancel"){_,_->

                            }
                            .create()
                            .show()
                    isFirstTime = false
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