package com.sms.pipe.view.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sms.pipe.R
import com.sms.pipe.databinding.LoginActivityBinding
import com.sms.pipe.di.loginModule
import com.sms.pipe.utils.ARG_IS_TERMS_NEED_ACCEPT
import com.sms.pipe.view.base.BaseActivity
import org.koin.core.module.Module


class LoginActivity : BaseActivity<LoginActivityViewModel, LoginActivityBinding>() {
    override val moduleList: List<Module>
        get() = listOf(loginModule)


    override fun getViewModelClass() = LoginActivityViewModel::class
    override fun getViewBinding() = LoginActivityBinding.inflate(layoutInflater)

    private lateinit var pagerAdapter: ViewPagerFragmentAdapter

    private val listOfTitles = listOf("Login", "Sign Up")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        didComeFromDeepLink()
    }

    private fun didComeFromDeepLink() {
        Log.d("LoginActivity ", "We Come from deepLink")
    }

    override fun initViews() {
        pagerAdapter = ViewPagerFragmentAdapter(this)
        pagerAdapter.addFragment(LoginFragment())
        pagerAdapter.addFragment(SignUpFragment())

        // set Orientation in your ViewPager2
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab: TabLayout.Tab, position: Int ->
            tab.text = listOfTitles[position]
        }.attach()
    }


    fun showTermsAndConditionsView() {
        PrivacyPoliciesBottomSheet().apply {
            isCancelable = false
            arguments = Bundle().apply { putBoolean(ARG_IS_TERMS_NEED_ACCEPT, false) }
            show(supportFragmentManager, "Terms&Conditions")
        }
    }


    override fun initObservers() {

        activityViewModel.loading.observe(this) {
            showOverlayProgress(it)
        }

        activityViewModel.isRegistered.observe(this) {
            if (it) {
                binding.pager.currentItem = 0
                signUpSuccess()
            }
        }
    }

    private fun signUpSuccess() {
        val snack = Snackbar.make(
            binding.root,
            "Signed Up successfully",
            Snackbar.LENGTH_LONG
        ).setActionTextColor(ContextCompat.getColor(this, R.color.white))
            .setBackgroundTint(ContextCompat.getColor(this, R.color.green))
        snack.anchorView = binding.guideline
        snack.show()
    }


    private fun showOverlayProgress(isActivate: Boolean) {
        if (isActivate) {
            binding.loader.isIndeterminate = true
            binding.loader.visibility = View.VISIBLE
            binding.overlay.visibility = View.VISIBLE
        } else {
            binding.loader.visibility = View.INVISIBLE
            binding.overlay.visibility = View.GONE
        }
    }

}