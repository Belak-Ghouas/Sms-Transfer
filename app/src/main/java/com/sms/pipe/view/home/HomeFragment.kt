package com.sms.pipe.view.home

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.di.homeModules
import com.sms.pipe.utils.ARG_SELECTED_APPLET
import com.sms.pipe.view.BottomSheetDeleteApplet
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.model.*
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    private  val mainViewModel: MainActivityViewModel by activityViewModels()

    override val moduleList: List<Module> = listOf(homeModules)

    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        fragmentViewModel.getSteps()
    }
    override fun initViews() {

        binding.card.setOnClickListener {
            mainViewModel.appletUi.value?.id?.let {
                onAppletSelected(it)
            }
        }

        binding.cardEmpty.setOnClickListener {
        }

        binding.steps.next.setOnClickListener {
            onNextClicked()
        }
    }

    private fun onNextClicked() {
        mActivity.createNewApplet()
    }


    override fun initObservers() {
        mainViewModel.appletUi.observe(viewLifecycleOwner){applet->
            applet?.let {
                setAppletUi(it)
            }?:run{
                setDefaultCard()
            }
        }

        fragmentViewModel.steps.observe(viewLifecycleOwner,::setOnBoarding)
    }

    private fun setAppletUi(applet: AppletUi) {
        binding.cardEmpty.visibility = View.GONE
        binding.card.visibility = View.VISIBLE
        binding.cardTitle.text = applet.appletName
        binding.cardDescription.text = getString(R.string.card_description, applet.channelName)
        applet.filters.forEach {
            when (it) {
                is AppletFilterSender -> binding.icSender.visibility = View.VISIBLE
                is AppletFilterContent -> binding.icSms.visibility = View.VISIBLE
            }
        }
        binding.rules.text =
            getString(R.string.card_rules, applet.filters.map { it.toString() }.toString())

    }

    private fun setOnBoarding(steps:List<Step>){
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

        }
    }

    private fun setDefaultCard(){
        binding.card.visibility = View.GONE
        if(fragmentViewModel.steps.value?.none { it.status != StepStatus.DONE } == true){
            binding.cardEmpty.visibility = View.VISIBLE
        }else{
            binding.cardEmpty.visibility = View.INVISIBLE
        }

    }

    private fun onAppletSelected(id:Long){

        val deleteAppletBottom = BottomSheetDeleteApplet()
        deleteAppletBottom.arguments = Bundle().apply {
            this.putLong(ARG_SELECTED_APPLET,id)
        }
        deleteAppletBottom.show(mActivity.supportFragmentManager,"DeleteApplet")
    }

    private fun Int.dpToPx(): Float {
        return this * (requireContext().resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}