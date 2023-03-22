package com.sms.pipe.view.login

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.sms.pipe.R
import com.sms.pipe.databinding.BottomSheetTermsConditionsBinding
import com.sms.pipe.utils.ARG_IS_TERMS_NEED_ACCEPT
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.base.BaseBottomSheet


class PrivacyPoliciesBottomSheet : BaseBottomSheet() {

    private lateinit var binding: BottomSheetTermsConditionsBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetTermsConditionsBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }


    override fun initViews() {
        if (requireArguments().getBoolean(ARG_IS_TERMS_NEED_ACCEPT, true)) {
            binding.close.visibility = View.GONE
            binding.accept.visibility = View.VISIBLE
        } else {
            binding.close.visibility = View.VISIBLE
            binding.accept.visibility = View.GONE
        }

        binding.tvConditionsContent.text = HtmlCompat.fromHtml(
            getString(R.string.terms_and_conditions_content),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        binding.tvConditionsContent.movementMethod = LinkMovementMethod.getInstance()
        setScrollEndListener()

        binding.accept.setOnClickListener {
            onTermsAccepted()
        }

        binding.close.setOnClickListener {
            dismissNow()
        }

    }

    private fun setScrollEndListener() {
        binding.scrollView.setOnScrollChangeListener { view, _, scrollY, _, _ ->
            val child = (view as ScrollView).getChildAt(0) as View

            // Calculate the scrolldiff
            val diff: Int = child.bottom - (binding.scrollView.height + scrollY)

            // if diff is zero, then the bottom has been reached
            if (diff <= 5) {
                // notify that we have reached the bottom
                binding.accept.isEnabled = true
            }
        }
    }


    private fun onTermsAccepted() {
        mainViewModel.onTermsAccepted()
        this.dismissNow()
    }

}