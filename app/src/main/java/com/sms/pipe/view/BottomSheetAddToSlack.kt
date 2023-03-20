package com.sms.pipe.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sms.pipe.databinding.BottomSheetAddToSlackBinding
import com.sms.pipe.view.base.BaseBottomSheet


class BottomSheetAddToSlack : BaseBottomSheet() {

    private lateinit var binding: BottomSheetAddToSlackBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetAddToSlackBinding.inflate(requireActivity().layoutInflater)
        return binding.root
    }

    override fun initViews() {
        binding.addToSlack.setOnClickListener {
            openWebView()
        }
    }

    private fun openWebView() {
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://sms-pipe-web.web.app/mobile/slack/internal?my_ticket=${mainViewModel.getToken()}")
        )
        requireActivity().startActivity(browserIntent)
        dismiss()
    }

}