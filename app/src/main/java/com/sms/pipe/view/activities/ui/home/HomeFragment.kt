package com.sms.pipe.view.activities.ui.home

import android.util.Log
import com.slack.api.Slack
import com.slack.api.SlackConfig
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.request.conversations.ConversationsListRequest
import com.slack.api.methods.response.chat.ChatPostMessageResponse
import com.slack.api.model.ConversationType
import com.sms.pipe.databinding.FragmentHomeBinding
import com.sms.pipe.framework.di.vmHomeModules
import com.sms.pipe.view.fragments.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.module.Module


class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {


    override val moduleList: List<Module> = listOf(vmHomeModules)

    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)

    override fun initViews() {

        binding.send.setOnClickListener {

            fragmentViewModel.sendMessage(binding.editText.text.toString())
        }
    }

    override fun initObservers() {
        fragmentViewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }
    }
}