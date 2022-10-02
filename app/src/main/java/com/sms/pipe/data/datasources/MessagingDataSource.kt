package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.ConversationModel
import com.sms.pipe.utils.Result

interface MessagingDataSource {

    fun initialize(token:String):Boolean

    suspend fun sendMessage(message:String,channelId :String)

    suspend fun getConversationList(): Result<List<ConversationModel>>
}