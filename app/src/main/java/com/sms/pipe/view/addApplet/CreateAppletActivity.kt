package com.sms.pipe.view.addApplet

import androidx.fragment.app.FragmentTransaction
import com.sms.pipe.databinding.ActivityCreateAppletBinding
import com.sms.pipe.di.vmCreateAppletModule
import com.sms.pipe.view.base.BaseActivity
import org.koin.core.module.Module


class CreateAppletActivity : BaseActivity<CreateAppletViewModel, ActivityCreateAppletBinding>() {
    override val moduleList: List<Module>
        get() = listOf(vmCreateAppletModule)

    override fun initViews() {
        val createFilterFragment = CreateFilterFragment()
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, createFilterFragment)
            .commit()
    }

    override fun initObservers() {
    }

    override fun getViewModelClass() = CreateAppletViewModel::class
    override fun getViewBinding() = ActivityCreateAppletBinding.inflate(layoutInflater)


    fun navigateToChooseSlack() {
        val chooseSlack = CreateSlackFragment()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, chooseSlack)
        if (!fragmentTransaction.isEmpty) {
            fragmentTransaction.addToBackStack(chooseSlack.javaClass.simpleName)
        }
        fragmentTransaction.commit()

    }

}