package com.sms.pipe.view.dashboard

import android.content.DialogInterface
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.databinding.BottomSheetAddRuleBinding

class BottomSheetAddRule : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAddRuleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = BottomSheetAddRuleBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.bottomSheetBiometric.let { view->
            val behavior = BottomSheetBehavior.from(view)
            behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        initViews()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    fun initViews() {
    }

}