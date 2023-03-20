package com.sms.pipe.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.sms.pipe.databinding.BottomSheetDeleteAppletBinding
import com.sms.pipe.utils.ARG_SELECTED_APPLET
import com.sms.pipe.view.base.BaseBottomSheet
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetDeleteApplet : BaseBottomSheet() {

    private lateinit var binding: BottomSheetDeleteAppletBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()
    private var appletUi: AppletUi? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDeleteAppletBinding.inflate(requireActivity().layoutInflater)
        appletUi = arguments?.getParcelable(ARG_SELECTED_APPLET)
        return binding.root
    }

    override fun initViews() {
        bindApplet(appletUi)
        binding.deleteBtn.setOnClickListener {
            appletUi?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mainViewModel.deleteApplet(it.id)
                    withContext(Dispatchers.Main) {
                        holdDeleteReturn(result)
                    }
                }

            } ?: run {
                dismissNow()
            }
        }

    }

    private fun bindApplet(applet: AppletUi?) {
        applet?.let {
            binding.appletName.text = it.appletName
            binding.channel.text = it.channelName
            binding.created.text = it.creationDate
        } ?: run {
            dismissNow()
        }

    }

    private fun holdDeleteReturn(deleted: Boolean) {
        if (deleted) {
            dismissNow()
        }
    }

}