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
import com.google.android.play.core.review.ReviewManagerFactory
import com.sms.pipe.R
import com.sms.pipe.databinding.ActivityMainBinding
import com.sms.pipe.di.mainActivityModule
import com.sms.pipe.utils.ARG_IS_TERMS_NEED_ACCEPT
import com.sms.pipe.utils.PLAY_STORE_BASE_URL
import com.sms.pipe.view.base.BaseActivity
import com.sms.pipe.view.base.BaseBottomSheet.Companion.ARG_HEIGHT_WRAP_CONTENT
import com.sms.pipe.view.base.BaseBottomSheet.Companion.ARG_IS_CANCELLABLE
import com.sms.pipe.view.base.BaseBottomSheet.Companion.ARG_IS_DRAGGABLE
import org.greenrobot.eventbus.EventBus
import org.koin.core.module.Module


const val PERMISSION_REQUEST_CODE = 99

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>() {

    var needRefresh = false
    private var isFirstTime = true

    override fun getViewModelClass() = MainActivityViewModel::class
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)
    override val moduleList: List<Module> = listOf(mainActivityModule)

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)
        activityViewModel.getApplet()
        activityViewModel.refresh()
        showPrivacyPolicy()
        checkReviews()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        if (needRefresh) {
            needRefresh = false
            showIndefiniteSnackBar("Need to refresh after adding you app to slack ", "refresh")
        }
    }

    override fun initObservers() {
        super.initObservers()
        activityViewModel.refresh.observe(this) {
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

    private fun showPrivacyPolicy() {
        if (activityViewModel.didUserReadPolicy().not()) {
            val args = Bundle()
            args.putBoolean(ARG_IS_CANCELLABLE, false)
            args.putBoolean(ARG_IS_DRAGGABLE, false)
            args.putBoolean(ARG_IS_TERMS_NEED_ACCEPT, true)
            args.putBoolean(ARG_HEIGHT_WRAP_CONTENT, false)
            navController.navigate(R.id.action_navigation_main_to_privacyPolicy, args)
        }
    }

    private fun checkReviews() {
        if (activityViewModel.needToShowReview()) {
            BottomSheetReview().apply {
                this.onReviewClicked = ::showInAppReview
            }.show(supportFragmentManager, "BottomSheetReview")
        }

    }


    private fun showInAppReview() {
        val manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(this, reviewInfo)
                flow.addOnFailureListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_BASE_URL)).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    this.startActivity(intent)
                }
                flow.addOnSuccessListener {
                    activityViewModel.onReviewSuccess()
                }
            } else {
                // There was some problem, log or handle the error code.
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_BASE_URL)).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                this.startActivity(intent)
            }
        }
    }

    private fun createNewApplet() {
        navController.navigate(R.id.action_navigation_main_to_bottomSheetChooser)
    }


    private fun askPermission() {
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

                    showIndefiniteSnackBar(
                        "We don't guarantee that the application will be functional without access to Received SMS",
                        "Ok"
                    )
                    isFirstTime = false
                }
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

    fun askUserPermissionWithDialog() {
        val icon = ContextCompat.getDrawable(this, R.drawable.ic_sms)
        icon?.setTint(ContextCompat.getColor(this, R.color.bar_nav_color))
        AlertDialog.Builder(this)
            .setIcon(icon)
            .setTitle(getString(R.string.sms_permission))
            .setMessage(getString(R.string.need_permission))
            .setPositiveButton("OK") { _, _ ->
                if (isFirstTime) {
                    askPermission()
                } else {
                    val intent =
                        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }

            }
            .setNegativeButton("cancel") { _, _ ->
                showIndefiniteSnackBar(
                    "We don't guarantee that the application will be functional without access to Received SMS",
                    "Ok"
                )
            }
            .create()
            .show()
    }

    fun showComingSoon() {
        showIndefiniteSnackBar(
            "This feature is not yet Available, Coming Soon.",
            "OK"
        )
    }

}