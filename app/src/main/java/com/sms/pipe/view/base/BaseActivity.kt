package com.sms.pipe.view.base

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.sms.pipe.R
import com.sms.pipe.data.models.LogoutEvent
import com.sms.pipe.services.SMSReceiver
import com.sms.pipe.utils.ARG_LOGOUT_UNAUTHORIZED
import com.sms.pipe.utils.ifNotEmpty
import com.sms.pipe.view.PERMISSION_REQUEST_CODE
import com.sms.pipe.view.PermissionDialog
import com.sms.pipe.view.login.SignActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import kotlin.reflect.KClass


abstract class BaseActivity<VM : BaseActivityViewModel, B : ViewBinding> : AppCompatActivity() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    protected lateinit var activityViewModel: VM
    lateinit var binding: B
    abstract val moduleList: List<Module>

    private var logoutReason: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.javaClass.toString(), "OnCreate")
        loadKoinModules(moduleList)
        activityViewModel = getViewModel(clazz = getViewModelClass())
        binding = getViewBinding()
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    abstract fun initViews()
    open fun initObservers() {
        activityViewModel.loggedOut.observe(this) {
            if (it) logout()
        }
    }

    abstract fun getViewModelClass(): KClass<VM>
    abstract fun getViewBinding(): B

    fun allPermissionsAreGranted(): List<String> {
        val permissions = arrayOf(Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS)
        val listToAsk = arrayListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                listToAsk.add(permission)
            }
        }
        return listToAsk
    }

    fun isReceiverEnabled(): Boolean {
        val pm: PackageManager = packageManager
        val componentName = ComponentName(this, SMSReceiver::class.java)
        val state = pm.getComponentEnabledSetting(componentName)
        return state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
    }

    /**
     * cancel all children coroutines of the job
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.coroutineContext.cancelChildren()
    }


    fun disableService(clazz: Class<*>) {
        val pm: PackageManager = packageManager
        val componentName = ComponentName(this, clazz)
        pm.setComponentEnabledSetting(
            componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }


    fun enableService(clazz: Class<*>) {
        val pm: PackageManager = packageManager
        val componentName = ComponentName(this, clazz)
        pm.setComponentEnabledSetting(
            componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    fun isSendSMSGranted() = Manifest.permission.SEND_SMS.isGranted()

    fun isReceiveSMSGranted() = Manifest.permission.RECEIVE_SMS.isGranted()

    fun isReadContactGranted() = Manifest.permission.READ_CONTACTS.isGranted()

    private fun String.isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            baseContext,
            this
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun askSendSMSPermission(onDismissed: (() -> Unit)? = null) {
        PermissionDialog.build(
            R.string.permission_dialog_send_sms,
            R.string.permission_dialog_send_sms_content
        ).apply {
            this.onAcceptClicked = {
                askPermission(listOf(Manifest.permission.SEND_SMS))
            }

            this.onDenyClicked = {
                showSnackBarPermission()
                onDismissed?.invoke()
            }
        }.show(supportFragmentManager, "Ask Send")
    }

    fun askReceiveSMSPermission(onDismissed: (() -> Unit)? = null) {
        PermissionDialog.build(
            R.string.permission_dialog_receive_sms,
            R.string.permission_dialog_receive_sms_content
        ).apply {
            this.onAcceptClicked = {
                askPermission(listOf(Manifest.permission.RECEIVE_SMS))
            }

            this.onDenyClicked = {
                showSnackBarPermission()
                onDismissed?.invoke()
            }
        }.show(supportFragmentManager, "Ask Receive")
    }

    private fun askPermission(permission: List<String>) {
        ActivityCompat.requestPermissions(
            this,
            permission.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
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
                    showSnackBarPermission()
                }
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun showSnackBarPermission() {
        showIndefiniteSnackBar(
            "We don't guarantee that the application will be functional without this permission",
            "Ok"
        )
    }


    fun showIndefiniteSnackBar(content: String, actionButton: String) {
        val snack = Snackbar.make(
            binding.root,
            content,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.setAction(actionButton) {
            snack.dismiss()
        }.setActionTextColor(ContextCompat.getColor(this, R.color.bar_nav_color))
        val view = snack.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.topMargin = 50
        view.layoutParams = params
        snack.show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageEvent(event: LogoutEvent) {
        logoutReason = ARG_LOGOUT_UNAUTHORIZED
        activityViewModel.logout()
    }

    open fun logout() {
        Log.e(this.javaClass.toString(), "Logout called ")
        val intent = Intent(this, SignActivity::class.java)
        logoutReason.ifNotEmpty {
            Bundle().apply {
                this.putBoolean(ARG_LOGOUT_UNAUTHORIZED, true)
                intent.putExtras(this)
            }
        }
        logoutReason = ""
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}