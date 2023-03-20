package com.sms.pipe.view.addApplet

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentCreateDeviceBinding
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.base.BaseFragmentViewModel
import org.koin.core.module.Module


class CreateDeviceFragment : BaseFragment<BaseFragmentViewModel, FragmentCreateDeviceBinding>() {


    private val createAppletViewModel: CreateAppletViewModel by activityViewModels()

    private val navController by lazy {
        findNavController()
    }

    override val moduleList: List<Module> get() = listOf()

    override fun getViewBinding() = FragmentCreateDeviceBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.validateBtn.setOnClickListener {
            createAppletViewModel.newApplet?.channelName = binding.receiver.text.toString()
            createAppletViewModel.storeApplet()
        }
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                calculateStateOfValidateButton()
            }
        }
        binding.receiver.addTextChangedListener(textWatcher)

    }

    override fun initObservers() {
        createAppletViewModel.appletStored.observe(viewLifecycleOwner) {
            if (it) {
                navController.navigate(R.id.action_CreateDeviceFragment_to_mainActivity)
                requireActivity().finish()
            }
        }
    }

    private fun calculateStateOfValidateButton() {
        val isTextPresent = binding.receiver.text.toString().isNotEmpty()
        binding.validateBtn.isEnabled = isTextPresent
    }
}