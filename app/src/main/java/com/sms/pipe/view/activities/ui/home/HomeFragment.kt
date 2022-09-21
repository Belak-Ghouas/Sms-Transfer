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

        CoroutineScope(Dispatchers.IO).launch {
            val slack = Slack.getInstance()

          val method = slack.methods("xoxb-3746919800806-3961811943894-cIKrRwVbDw4LlM81JzPtPznQ")
            // Build a request object
            // Build a request object
            method.conversationsList {
                ConversationsListRequest.builder()
            }
            val request = ChatPostMessageRequest.builder()
                .channel("#test-android-app") // Use a channel ID `C1234567` is preferable
                .text(":wave: Hi from a bot written in Java!")
                .build()

// Get a response as a Java object

// Get a response as a Java object
            val responses: ChatPostMessageResponse = method.chatPostMessage(request)

            val types: MutableList<ConversationType> = ArrayList()
            types.add(ConversationType.PUBLIC_CHANNEL)
             types.add(ConversationType.PRIVATE_CHANNEL)

            val builder =
                ConversationsListRequest.builder().token("xoxb-3746919800806-3961811943894-cIKrRwVbDw4LlM81JzPtPznQ").types(types).limit(5)

            val listResponse = slack.methods().conversationsList(builder.build())
            Log.d("test",listResponse.toString())
        }

    }

    override fun initObservers() {
        fragmentViewModel.text.observe(viewLifecycleOwner) {
            binding.textHome.text = it
        }
    }
}