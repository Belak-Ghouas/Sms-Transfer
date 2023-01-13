package com.sms.pipe.view.addApplet

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.R
import com.sms.pipe.databinding.BottomSheetCustomizeMessageBinding

class CustomizeMessageBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetCustomizeMessageBinding

    var onReviewClicked: (() -> Unit)? = null

    private var spannedLength = 0
    private  var chipLength:Int = 4

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetCustomizeMessageBinding.inflate(requireActivity().layoutInflater)
        initViews()
        return binding.root
    }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


    override fun onStart() {
        super.onStart()
        binding.container.let { view ->
            val behavior = BottomSheetBehavior.from(view)
            behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    fun initViews() {
        binding.inputCustomize.addTextChangedListener(

            onTextChanged = {text, start, before, count ->
                if (text?.length == spannedLength - chipLength)
                {
                    spannedLength = text.length
                }

            },
            afterTextChanged = {

            }

        )
    }

}