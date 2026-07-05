package com.sms.pipe.view.addApplet

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
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
        // Opt into edge-to-edge so we control how insets are applied.
        // Without this the system pads the window automatically on Android 15,
        // which can produce inconsistent results across API levels.
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        // Apply the navigation-bar inset as bottom padding on the root view.
        // Because the NavHostFragment fills this view (0dp × 0dp with all 4
        // constraints), every fragment's bottom boundary will sit above the
        // home indicator — so bottom-pinned buttons are never hidden.
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val navBarHeight = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = statusBarHeight, bottom = navBarHeight)
            insets
        }

        val navGraph = navController.navInflater.inflate(R.navigation.add_applet_navigation)
        navGraph.setStartDestination(R.id.CreateFilterFragment)
        navController.setGraph(navGraph, Bundle().also {
            it.putString(ARG_APPLET_TYPE, argAppletType)
        })
        navController.navigate(R.id.CreateFilterFragment, Bundle().also {
            it.putString(ARG_APPLET_TYPE, argAppletType)
        }, navOptions = navOptions {
            popUpTo(R.id.CreateFilterFragment) {
                inclusive = true
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun initViews() {}

    override fun getViewModelClass() = CreateAppletViewModel::class
    override fun getViewBinding() = ActivityCreateAppletBinding.inflate(layoutInflater)
}