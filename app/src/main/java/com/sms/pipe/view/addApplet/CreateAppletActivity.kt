package com.sms.pipe.view.addApplet

import androidx.fragment.app.FragmentTransaction
import com.sms.pipe.databinding.ActivityCreateAppletBinding
import com.sms.pipe.di.createAppletModule
import com.sms.pipe.utils.ARG_APPLET_TYPE
import com.sms.pipe.view.base.BaseActivity
import com.sms.pipe.view.model.AppletType
import org.koin.core.module.Module


class CreateAppletActivity : BaseActivity<CreateAppletViewModel, ActivityCreateAppletBinding>() {
    override val moduleList: List<Module>
        get() = listOf(createAppletModule)

    private val argAppletType : AppletType by lazy {
      enumValueOf(intent.extras?.getString(ARG_APPLET_TYPE)?:AppletType.UNKNOWN.name) //intent.extras!!.getString(ARG_APPLET_TYPE)
    }

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


    fun navigateToReceiver() {
        activityViewModel.newApplet?.type = argAppletType
        when(argAppletType){
            AppletType.SLACK ->{
                navigateToSlackView()
            }
            AppletType.MAIL->{

            }

            AppletType.DEVICE->{
                if(isSendSMSGranted()){
                    navigateToDeviceView()
                }else{
                    askSendSMSPermission()
                }

            }
        }

    }

    private fun navigateToSlackView(){
        val chooseSlack = CreateSlackFragment()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, chooseSlack)
        if (!fragmentTransaction.isEmpty) {
            fragmentTransaction.addToBackStack(chooseSlack.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

    private fun navigateToDeviceView(){
        val chooseContact = CreateDeviceFragment()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentContainer.id, chooseContact)
        if (!fragmentTransaction.isEmpty) {
            fragmentTransaction.addToBackStack(chooseContact.javaClass.simpleName)
        }
        fragmentTransaction.commit()
    }

}