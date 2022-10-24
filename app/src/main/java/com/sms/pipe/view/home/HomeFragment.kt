package com.sms.pipe.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sms.pipe.R
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.di.vmHomeModules
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.addApplet.CreateAppletActivity
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.model.AppletFilterContent
import com.sms.pipe.view.model.AppletFilterSender
import com.sms.pipe.view.model.AppletUi
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private var needRefresh = false
    private val mActivity : MainActivity by lazy {
        requireActivity() as MainActivity
    }
    override val moduleList: List<Module> = listOf(vmHomeModules)

    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentViewModel.getApplet()
    }

    override fun initViews() {
        binding.card.setOnLongClickListener {
            binding.card.isChecked = !binding.card.isChecked
            true
        }

        binding.cardEmpty.setOnClickListener {
            if(fragmentViewModel.hasSlack.value == true){
                if(mActivity.allPermissionsAreGranted().isEmpty()){
                    val intent = Intent(this.context, CreateAppletActivity::class.java)
                    mActivity.startActivity(intent)
                }else{
                   mActivity.askPermission()
                }

            }else{
                val snack = Snackbar.make(requireView(), "You need to add first you app to slack ", Snackbar.LENGTH_LONG)
                snack.anchorView = binding.guideline
                snack.show()
            }
        }

        binding.addToSlack.setOnClickListener {
            openWebView()
            needRefresh = true
        }
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

    private fun openWebView() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sms-pipe-web.web.app/login"))
        startActivity(browserIntent)
    }

    override fun initObservers() {
               fragmentViewModel.appletUi.observe(viewLifecycleOwner,::setAppletUi)

        fragmentViewModel.hasSlack.observe(viewLifecycleOwner){
            if(it){
                binding.addToSlack.visibility = View.GONE
            }else{
                binding.addToSlack.visibility =  View.VISIBLE
            }
        }
    }

    private fun setAppletUi(applet: AppletUi) {
        binding.cardEmpty.visibility = View.GONE
        binding.card.visibility = View.VISIBLE
        binding.cardTitle.text = applet.appletName
        binding.cardDescription.text = getString(R.string.card_description,applet.channelName)
        applet.filters.forEach{
            when(it){
                is AppletFilterSender -> binding.icSender.visibility = View.VISIBLE
                is AppletFilterContent->  binding.icSms.visibility = View.VISIBLE
            }
        }
        binding.rules.text = getString(R.string.card_rules,applet.filters.map { it.toString() }.toString())

    }
}