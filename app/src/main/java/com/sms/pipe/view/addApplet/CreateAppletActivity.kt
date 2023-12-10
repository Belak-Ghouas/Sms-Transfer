package com.sms.pipe.view.addApplet

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.sms.pipe.R
import com.sms.pipe.databinding.ActivityCreateAppletBinding
import com.sms.pipe.di.createAppletModule
import com.sms.pipe.utils.ARG_APPLET_TYPE
import com.sms.pipe.view.base.BaseActivity
import com.sms.pipe.view.model.AppletType
import org.koin.core.module.Module


class CreateAppletActivity : BaseActivity<CreateAppletViewModel, ActivityCreateAppletBinding>() {
    override val moduleList: List<Module>
        get() = listOf(createAppletModule)

    private val argAppletType: String by lazy {
        intent.extras?.getString(ARG_APPLET_TYPE) ?: AppletType.UNKNOWN.name
    }

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment_activity_add_applet)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navGraph = navController.navInflater.inflate(R.navigation.add_applet_navigation)
        navGraph.setStartDestination(R.id.CreateFilterFragment)
        navController.setGraph(navGraph,Bundle().also {
            it.putString(ARG_APPLET_TYPE, argAppletType)
        })
        navController.navigate(R.id.CreateFilterFragment, Bundle().also {
            it.putString(ARG_APPLET_TYPE, argAppletType)
        },navOptions = navOptions {
            popUpTo(R.id.CreateFilterFragment) {
                inclusive = true
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }
    override fun initViews() {
    }

    override fun getViewModelClass() = CreateAppletViewModel::class
    override fun getViewBinding() = ActivityCreateAppletBinding.inflate(layoutInflater)

}