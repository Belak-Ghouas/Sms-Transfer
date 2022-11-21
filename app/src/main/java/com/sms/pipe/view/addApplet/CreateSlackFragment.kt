package com.sms.pipe.view.addApplet

import android.content.Intent
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.sms.pipe.R
import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.databinding.FragmentCreateSlackBinding
import com.sms.pipe.view.MainActivity
import com.sms.pipe.view.base.BaseFragment
import com.sms.pipe.view.base.BaseFragmentViewModel
import com.sms.pipe.view.model.AppletType
import org.koin.core.module.Module
import android.R as AnR


class CreateSlackFragment : BaseFragment<BaseFragmentViewModel, FragmentCreateSlackBinding>() {

    private val channelAdapter :ArrayAdapter<String> by lazy {
      ArrayAdapter(requireContext(),AnR.layout.simple_spinner_item)
   }

    private val createAppletViewModel : CreateAppletViewModel by activityViewModels()

    override val moduleList: List<Module> get() = listOf()

    override fun getViewBinding() = FragmentCreateSlackBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.tvSlackChannel.setAdapter(channelAdapter)
        binding.tvSlackChannel.setOnItemClickListener{ adapter,_,position,_->
           onItemSelected(adapter.getItemAtPosition(position) as String)
        }

        binding.validateBtn.setOnClickListener {
            createAppletViewModel.storeApplet()
        }
    }

    override fun initObservers() {
        createAppletViewModel.channels.observe(viewLifecycleOwner){
            setChannelData(it)
        }

        createAppletViewModel.appletStored.observe(viewLifecycleOwner){
            if (it){
                val intent = Intent(requireContext(),MainActivity::class.java)
                activity?.startActivity(intent)
                activity?.finish()
            }
        }
    }

    private fun onItemSelected(channel:String) {
        binding.selectedChannel.text = getString(R.string.selected_channel,channel)
        createAppletViewModel.newApplet?.channelName = channel
        createAppletViewModel.newApplet?.type = AppletType.SLACK
        binding.validateBtn.isEnabled = true
    }

    private fun setChannelData(channels: List<ChannelModel>) {
        channelAdapter.addAll(channels.map { it.name })
        channelAdapter.notifyDataSetChanged()
    }
}