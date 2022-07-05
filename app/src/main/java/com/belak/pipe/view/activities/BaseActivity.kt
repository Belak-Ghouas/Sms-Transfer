package com.belak.pipe.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.belak.pipe.view.viewmodels.BaseActivityViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass


abstract class BaseActivity <VM : BaseActivityViewModel, B : ViewBinding> : AppCompatActivity() {

    private val job = Job()
    protected val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    private lateinit var activityViewModel: VM
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

    abstract fun getViewBinding(): B
    abstract fun initViews()
    abstract fun initObservers()


    private fun getViewModelClass(): KClass<VM> {
        return (((javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]) as Class<VM>).kotlin
    }


    /**
     * cancel all children coroutines of the job
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.coroutineContext.cancelChildren()
    }
}