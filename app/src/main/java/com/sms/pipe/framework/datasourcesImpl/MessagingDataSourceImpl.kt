package com.sms.pipe.framework.datasourcesImpl

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.response.chat.ChatPostMessageResponse
import com.sms.pipe.data.datasources.MessagingDataSource
import com.sms.pipe.data.models.ConversationModel

class MessagingDataSourceImpl(): MessagingDataSource {
    private var slack :MethodsClient? = null

    override fun initialize(token: String) {
       slack = Slack.getInstance().methods(token)
    }


    override suspend fun sendMessage(message: String, channelId:String) {
        val request = ChatPostMessageRequest.builder()
            .channel(channelId) // Use a channel ID `C1234567` is preferable
            .text(message)
            .build()
        val responses: ChatPostMessageResponse? = slack?.chatPostMessage(request)
    }

    override suspend fun getConversationList(): List<ConversationModel> {
        TODO("Not yet implemented")
    }
}