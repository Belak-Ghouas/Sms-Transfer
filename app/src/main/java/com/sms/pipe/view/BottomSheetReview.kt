package com.sms.pipe.view

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.R
import com.sms.pipe.databinding.BottomSheetReviewBinding

class BottomSheetReview : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetReviewBinding

    var onReviewClicked: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetReviewBinding.inflate(requireActivity().layoutInflater)
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

        binding.later.setOnClickListener {
            dismissNow()
        }

        binding.dislike.setOnClickListener {
            dismissNow()
        }

        binding.like.setOnClickListener {
            onReviewClicked?.invoke()
            dismissNow()
        }
    }

}