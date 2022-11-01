package com.sms.pipe.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.databinding.BottomSheetDeleteAppletBinding
import com.sms.pipe.utils.ARG_SELECTED_APPLET
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BottomSheetDeleteApplet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDeleteAppletBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetDeleteAppletBinding.inflate(requireActivity().layoutInflater)
        id = arguments?.getLong(ARG_SELECTED_APPLET)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }


    override fun onStart() {
        super.onStart()
        binding.bottomSheetBiometric.let { view ->
            val behavior = BottomSheetBehavior.from(view)
            behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun initObservers() {
        mainViewModel.appletUi.observe(viewLifecycleOwner, ::bindApplet)
    }

    fun initViews() {

        binding.deleteBtn.setOnClickListener {
            id?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mainViewModel.deleteApplet(it)
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