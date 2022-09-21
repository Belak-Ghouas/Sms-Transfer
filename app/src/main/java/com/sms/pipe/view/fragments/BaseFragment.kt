package com.sms.pipe.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass


abstract class BaseFragment <VM : BaseFragmentViewModel, B : ViewBinding> : Fragment() {

    private val job = Job()
    protected val coroutineScope = CoroutineScope(Dispatchers.Main + job)
    protected lateinit var fragmentViewModel: VM
    lateinit var binding: B
    abstract val moduleList : List<Module>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(moduleList)
        fragmentViewModel = getViewModel(clazz = getViewModelClass())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding()
        initViews()
        initObservers()
       return binding.root
    }

    abstract fun getViewBinding(): B
    abstract fun initViews()
    abstract fun initObservers()


    /**
     * using reflection to find the class of ViewModel
     * and like that we can instantiate it on the baseFragment
     * we don't have to add any code in the child fragments
     */
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