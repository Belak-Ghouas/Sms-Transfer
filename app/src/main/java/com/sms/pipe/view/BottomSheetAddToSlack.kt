package com.sms.pipe.view

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.databinding.BottomSheetAddToSlackBinding

class BottomSheetAddToSlack : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetAddToSlackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = BottomSheetAddToSlackBinding.inflate(requireActivity().layoutInflater)
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


    fun initViews() {
        binding.addToSlack.setOnClickListener {
           openWebView()
        }
    }

    private fun openWebView() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sms-pipe-web.web.app/login"))
        startActivity(browserIntent)
        dismiss()
    }

}