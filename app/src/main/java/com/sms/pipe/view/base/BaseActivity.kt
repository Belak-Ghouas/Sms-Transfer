package com.sms.pipe.view.base

import android.Manifest
import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.sms.pipe.services.SMSReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import kotlin.reflect.KClass


abstract class BaseActivity <VM : BaseActivityViewModel,B : ViewBinding> : AppCompatActivity() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    protected lateinit var activityViewModel: VM
    lateinit var binding: B
    abstract val moduleList : List<Module>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
        loadKoinModules(moduleList)
        activityViewModel = getViewModel(clazz = getViewModelClass())
        initViews()
        initObservers()
    }

    abstract fun initViews()
    abstract fun initObservers()
    abstract fun getViewModelClass(): KClass<VM>
    abstract fun getViewBinding(): B

    fun allPermissionsAreGranted():List<String>{
        val permissions = arrayOf(
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS)
        val listToAsk = arrayListOf<String>()
        for (permission in permissions){
            if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
                listToAsk.add(permission)
            }
        }
        return listToAsk
    }

    fun isReceiverEnabled():Boolean{
        val pm: PackageManager = packageManager
        val componentName = ComponentName(this, SMSReceiver::class.java)
        val state = pm.getComponentEnabledSetting(componentName)
       return  state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
    }
    /**
     * cancel all children coroutines of the job
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.coroutineContext.cancelChildren()
    }


    fun disableService(clazz:Class<*>){
        val pm: PackageManager = packageManager
        val componentName = ComponentName(this, clazz)
        pm.setComponentEnabledSetting(
            componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED ,
            PackageManager.DONT_KILL_APP)
    }


    fun enableService(clazz:Class<*>){
        val pm: PackageManager = packageManager
        val componentName = ComponentName(this, clazz)
        pm.setComponentEnabledSetting(
            componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP)
    }


}