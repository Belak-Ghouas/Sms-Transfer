package com.sms.pipe.data.datasourcesImpl

import android.util.Log
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.SlackApiException
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.methods.request.conversations.ConversationsListRequest
import com.slack.api.methods.response.chat.ChatPostMessageResponse
import com.slack.api.model.Conversation
import com.slack.api.model.ConversationType
import com.sms.pipe.data.datasources.MessagingDataSource
import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.utils.ErrorCodes.MESSAGING_API_CALL_FAILED
import com.sms.pipe.utils.MessagingIsNotInitializedException
import com.sms.pipe.utils.Result
import java.io.IOException


class MessagingDataSourceImpl : MessagingDataSource {

    private var slack :MethodsClient? = null

    override fun initialize(token: String):Boolean {
       slack = Slack.getInstance().methods(token)
        return true
    }


    override suspend fun sendMessage(message: String, channelId:String) {
       slack?.let {
           val request = ChatPostMessageRequest.builder()
               .channel(channelId) // Use a channel ID `C1234567` is preferable
               .text(message)
               .build()
           try {
               val responses: ChatPostMessageResponse? = it.chatPostMessage(request)
               Log.d("PostMessage Response",responses.toString())
           }catch (exception:Exception){
              Log.e("MessagingDataSource",exception.toString())
           }

       }?: kotlin.run {
           throw MessagingIsNotInitializedException()
       }

    }

    override suspend fun getConversationList():Result<List<ChannelModel>>{
        val types: MutableList<ConversationType> = ArrayList()
        types.add(ConversationType.PUBLIC_CHANNEL)
        types.add(ConversationType.PRIVATE_CHANNEL)
        val builder = ConversationsListRequest.builder().types(types).limit(5)
        slack?.let { methodsClient ->
            try {
                val listResponse = methodsClient.conversationsList(builder.build())
                if (!listResponse.isOk) {
                   Log.e("API call failed: " , listResponse.error.toString())
                }

                val channels: List<Conversation> = listResponse.channels
                Log.d("Conversation", channels.map { it.name }.toString())
                return  Result.Success(channels.map { ChannelModel(it.name) } )
            } catch (e: IOException) {
                Log.e("API call failed: " , e.toString())
            } catch (e: SlackApiException) {
                Log.e("API call failed: " , e.toString())
            }

        } ?: kotlin.run {
            throw MessagingIsNotInitializedException()
        }
        return Result.Failure(MESSAGING_API_CALL_FAILED,"failed to get conversation list")
    }
}