package com.sms.pipe.view.addApplet

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sms.pipe.R
import com.sms.pipe.databinding.BottomSheetChooseReceiverBinding
import com.sms.pipe.utils.CircularRecyclerViewListener
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.model.AppletType


class ChooseReceiverBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetChooseReceiverBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private val mActivity by lazy {
        requireActivity() as MainActivity
    }

    private val data: List<AppletType> by lazy {
        listOf(AppletType.MAIL, AppletType.DEVICE, AppletType.SLACK)
    }

    private val receiverAdapter: ChooseReceiverAdapter by lazy {
        ChooseReceiverAdapter(::onAppletTypeSelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetChooseReceiverBinding.inflate(requireActivity().layoutInflater)
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
        initViews()
    }


    fun initViews() {

        binding.recycler.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            this.adapter = receiverAdapter
            //make infinite circular recycler view
            val listener = CircularRecyclerViewListener(data.size)
            this.addOnScrollListener(listener)
            // to make scroll one element a time
            //val snapHelper: SnapHelper = PagerSnapHelper()
            // snapHelper.attachToRecyclerView(binding.recycler)
        }
        receiverAdapter.setData(data)
    }

    private fun openWebView() {
    }

    private fun onAppletTypeSelected(appletType: AppletType) {
        Log.d("Abdelhak", "onclick applet : $appletType")
        if (mActivity.isReceiveSMSGranted()) {
            when (appletType) {
                AppletType.SLACK -> {
                    if (mainViewModel.hasSlack.value == true) {
                        mActivity.openCreateAppletActivity(appletType)
                        dismissNow()
                    } else {
                        mActivity.openAddToSlackBottomSheet()
                        dismissNow()
                    }
                }

                AppletType.DEVICE -> {
                    mActivity.openCreateAppletActivity(appletType)
                    dismissNow()
                }
                AppletType.MAIL ->{
                    mActivity.showComingSoon()
                    dismissNow()
                }
                else -> {
                    Log.d("ChooseReceiverBottomSheet", "On Unknown clicked must never happen")
                }
            }
        } else {
            mActivity.askReceiveSMSPermission()
        }

    }

}