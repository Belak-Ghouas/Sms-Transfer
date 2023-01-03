package com.sms.pipe.view.home

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.di.homeModules
import com.sms.pipe.utils.ARG_SELECTED_APPLET
import com.sms.pipe.view.BottomSheetDeleteApplet
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.model.AppletType
import com.sms.pipe.view.model.AppletUi
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    private val mainViewModel: MainActivityViewModel by activityViewModels()

    override val moduleList: List<Module> = listOf(homeModules)

    private val data: List<AppletType> by lazy {
        listOf(AppletType.MAIL, AppletType.DEVICE, AppletType.SLACK)
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(::onAppletSelected, data)
    }

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        mActivity.binding.swiperefresh.isEnabled = true
    }

    override fun initViews() {
        binding.recycler.apply {
            val linearLayoutManager = LinearLayoutManager(context)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            this.adapter = homeAdapter
        }

        binding.steps.next.setOnClickListener {
            onNextClicked()
        }
    }

    private fun onNextClicked() {
        mActivity.createNewApplet()
    }


    override fun initObservers() {
        mainViewModel.appletUi.observe(viewLifecycleOwner) { applets ->
            homeAdapter.setData(applets)
        }
    }

    private fun onAppletSelected(applet: AppletUi) {

        val deleteAppletBottom = BottomSheetDeleteApplet()
        deleteAppletBottom.arguments = Bundle().apply {
            this.putLong(ARG_SELECTED_APPLET, applet.id)
        }
        deleteAppletBottom.show(mActivity.supportFragmentManager, "DeleteApplet")
    }

    private fun Int.dpToPx(): Float {
        return this * (requireContext().resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}