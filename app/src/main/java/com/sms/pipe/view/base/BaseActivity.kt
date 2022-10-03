package com.sms.pipe.view.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
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

    /**
     * cancel all children coroutines of the job
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.coroutineContext.cancelChildren()
    }


}