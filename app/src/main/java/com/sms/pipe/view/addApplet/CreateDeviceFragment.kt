package com.sms.pipe.view.addApplet

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import com.sms.pipe.databinding.FragmentCreateDeviceBinding
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.base.BaseFragmentViewModel
import org.koin.core.module.Module


class CreateDeviceFragment : BaseFragment<BaseFragmentViewModel, FragmentCreateDeviceBinding>() {


    private val createAppletViewModel: CreateAppletViewModel by activityViewModels()

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
                val intent = Intent(requireContext(), MainActivity::class.java)
                activity?.startActivity(intent)
            }
        }
    }

    private fun calculateStateOfValidateButton() {
        val isTextPresent = binding.receiver.text.toString().isNotEmpty()
        binding.validateBtn.isEnabled = isTextPresent
    }
}