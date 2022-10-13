package com.sms.pipe.view.addApplet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sms.pipe.databinding.FragmentCreateFilterBinding
import com.sms.pipe.di.vmCreateFilterModules
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.model.AppletFilterType
import com.sms.pipe.view.model.AppletUi
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.module.Module

class CreateFilterFragment : BaseFragment<CreateFilterViewModel, FragmentCreateFilterBinding>() {

    private val myAppletFilter:ArrayList<AppletFilterType> = ArrayList()
    private val createAppletViewModel by sharedViewModel<CreateAppletViewModel>()
    private val activity : CreateAppletActivity by lazy {
        requireActivity() as CreateAppletActivity
    }
    override val moduleList: List<Module>
        get() = listOf(vmCreateFilterModules)

    override fun getViewBinding() = FragmentCreateFilterBinding.inflate(layoutInflater)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentCreateFilterBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun initObservers() {
    }

    override fun initViews() {
        binding.validateBtn.setOnClickListener {
            createApplet()
        }
        binding.chipNumberFilter.setOnCheckedChangeListener{_,checked->
            calculateStateOfValidateButton()
            if(checked){
                myAppletFilter.add(AppletFilterType.BY_SENDER)
                binding.senderNumberInput.visibility = View.VISIBLE
                binding.tvPhoneNumberDescription.visibility = View.VISIBLE
            }else {
                myAppletFilter.remove(AppletFilterType.BY_SENDER)
                binding.senderNumberInput.visibility = View.GONE
                binding.tvPhoneNumberDescription.visibility =View.GONE
                binding.senderPhoneNumber.text?.clear()
            }
        }
        binding.chipSmsContentFilter.setOnCheckedChangeListener{_,checked->
            calculateStateOfValidateButton()
            if(checked){
                myAppletFilter.add(AppletFilterType.BY_CONTENT)
                binding.smsContentInput.visibility = View.VISIBLE
                binding.tvSmsContentDescription.visibility = View.VISIBLE
            }else{
                myAppletFilter.remove(AppletFilterType.BY_CONTENT)
                binding.smsContentInput.visibility = View.GONE
                binding.tvSmsContentDescription.visibility = View.GONE
                binding.senderSmsContent.text?.clear()
            }
        }
        val textWatcher = object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                calculateStateOfValidateButton()
            }
        }
        binding.senderSmsContent.addTextChangedListener(textWatcher)
        binding.senderPhoneNumber.addTextChangedListener(textWatcher)
    }

    private fun createApplet() {
        val myApplet = AppletUi(
            appletName = "Default",
            filters = myAppletFilter,
            isEnabled = true
        )
        createAppletViewModel.newApplet = myApplet
        activity.navigateToChooseSlack()
    }


    private fun calculateStateOfValidateButton(){
        if(binding.chipNumberFilter.isChecked){
            val isTextPresent = binding.senderPhoneNumber.text.toString().isNotEmpty()
            if(isTextPresent){
                binding.validateBtn.isEnabled = true
            }else{
                binding.validateBtn.isEnabled = false
                return
            }
        }

        if(binding.chipSmsContentFilter.isChecked ) {
            val isTextPresent = binding.senderSmsContent.text.toString().isNotEmpty()
            if (isTextPresent) {
                binding.validateBtn.isEnabled = true
            } else {
                binding.validateBtn.isEnabled = false
                return
            }
        }
    }




}