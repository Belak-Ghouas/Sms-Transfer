package com.sms.pipe.view.login

import android.app.Dialog
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ScrollView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.R
import com.sms.pipe.databinding.BottomSheetTermsConditionsBinding
import com.sms.pipe.view.MainActivityViewModel


class PrivacyPoliciesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetTermsConditionsBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { parent ->
                val behaviour = BottomSheetBehavior.from(parent)
                setupFullHeight(parent)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.skipCollapsed = true
                behaviour.isDraggable = false
            }
        }
        return dialog
    }


    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetTermsConditionsBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }


    private fun initObservers() {
    }

    fun initViews() {
        binding.tvConditionsContent.text = HtmlCompat.fromHtml(getString(R.string.terms_and_conditions_content), HtmlCompat.FROM_HTML_MODE_LEGACY)
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