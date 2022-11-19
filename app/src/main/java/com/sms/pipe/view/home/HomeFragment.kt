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
import com.sms.pipe.view.model.Step
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
        fragmentViewModel.getSteps()
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

        fragmentViewModel.steps.observe(viewLifecycleOwner, ::setOnBoarding)
    }

    private fun setOnBoarding(steps: List<Step>) {
        /*
        if(steps.none { it.status != StepStatus.DONE }){
            binding.steps.root.visibility = View.GONE
        }else{
            binding.steps.iconFirstStep.setImageResource(steps[0].getIcon())
            binding.steps.iconFirstStep.background = ContextCompat.getDrawable(requireContext(), steps[0].getBackground())
            binding.steps.iconFirstStep.setPadding(steps[0].getPadding().dpToPx().toInt())

            binding.steps.iconSecondStep.setImageResource(steps[1].getIcon())
            binding.steps.iconSecondStep.background = ContextCompat.getDrawable(requireContext(), steps[1].getBackground())
            binding.steps.iconSecondStep.setPadding(steps[1].getPadding().dpToPx().toInt())

            binding.steps.iconThirdStep.setImageResource(steps[2].getIcon())
            binding.steps.iconThirdStep.background = ContextCompat.getDrawable(requireContext(), steps[2].getBackground())
            binding.steps.iconThirdStep.setPadding(steps[2].getPadding().dpToPx().toInt())

        }*/
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