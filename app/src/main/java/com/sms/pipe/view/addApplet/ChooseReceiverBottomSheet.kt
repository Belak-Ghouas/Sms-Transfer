package com.sms.pipe.view.addApplet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.contentsquare.android.Contentsquare
import com.sms.pipe.R
import com.sms.pipe.databinding.BottomSheetChooseReceiverBinding
import com.sms.pipe.utils.ARG_APPLET_TYPE
import com.sms.pipe.utils.CircularRecyclerViewListener
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.base.BaseBottomSheet
import com.sms.pipe.view.model.AppletType


class ChooseReceiverBottomSheet : BaseBottomSheet() {

    private lateinit var binding: BottomSheetChooseReceiverBinding
    private val mainViewModel: MainActivityViewModel by activityViewModels()

    private val navController by lazy {
        findNavController()
    }

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

    override fun initViews() {
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

    override fun onResume() {
        super.onResume()
        Contentsquare.send("Chooser screen")
    }
    private fun onAppletTypeSelected(appletType: AppletType) {
        if (mActivity.isReceiveSMSGranted()) {
            when (appletType) {
                AppletType.SLACK -> {
                    if (mainViewModel.hasSlack.value == true) {
                        navigateToCreateApplet(appletType)
                        dismissNow()
                    } else {
                        mActivity.needRefresh = true
                        navController.navigate(R.id.action_bottomSheetChooser_to_bottomSheetAddToSlack)
                    }
                }

                AppletType.DEVICE -> {
                    navigateToCreateApplet(appletType)
                    dismissNow()
                }
                AppletType.MAIL -> {
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

    private fun navigateToCreateApplet(appletType:AppletType){
        val args = Bundle()
        args.putString(ARG_APPLET_TYPE, appletType.name)
        navController.navigate(R.id.action_bottomSheetChooser_to_createAppletActivity,args)
    }

}