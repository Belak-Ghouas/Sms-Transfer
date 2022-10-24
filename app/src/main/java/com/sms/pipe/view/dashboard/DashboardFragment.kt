package com.sms.pipe.view.dashboard

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentDashboardBinding
import com.sms.pipe.di.vmDashboardModules
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.addApplet.CreateAppletActivity
import com.sms.pipe.view.base.BaseFragment
import org.koin.core.module.Module


class DashboardFragment : BaseFragment<DashboardViewModel, FragmentDashboardBinding>() {


    private var needRefresh = false
    override val moduleList: List<Module> = listOf(vmDashboardModules)

    override fun getViewBinding() =FragmentDashboardBinding.inflate(layoutInflater)

    private val activity : MainActivity by lazy {
        requireActivity() as MainActivity
    }

    override fun onResume() {
        super.onResume()
        if(needRefresh){
            needRefresh = false
            showSnackBarRefresh()
        }
    }

    private fun showSnackBarRefresh() {
       val snack = Snackbar.make(requireView(), "Need to refresh after adding you app to slack ", Snackbar.LENGTH_INDEFINITE)

        snack.setAction("Refresh") {
            fragmentViewModel.refresh()
            snack.dismiss()
        }.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.bar_nav_color))
        snack.anchorView = binding.guideline
        snack.show()


    }

    override fun initViews() {

        binding.addNewRule.setOnClickListener {
            val intent = Intent(this.context, CreateAppletActivity::class.java)
            activity.startActivity(intent)
        }
        binding.addToSlack.setOnClickListener {
            openWebView()
            needRefresh = true
        }
    }

    private fun openWebView() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sms-pipe-web.web.app/login"))
        startActivity(browserIntent)
    }

    override fun initObservers() {
        fragmentViewModel.hasSlack.observe(viewLifecycleOwner){
            if(it){
                binding.addNewRule.visibility = View.VISIBLE
                binding.dashboardDescriptionCreateApplet.visibility = View.VISIBLE
                binding.addToSlack.visibility = View.GONE
                binding.dashboardDescriptionSlack.visibility = View.GONE
            }else{
                binding.addNewRule.visibility = View.GONE
                binding.dashboardDescriptionCreateApplet.visibility = View.GONE
                binding.addToSlack.visibility =  View.VISIBLE
                binding.dashboardDescriptionSlack.visibility =  View.VISIBLE

            }
        }
    }

}