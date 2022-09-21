package com.sms.pipe.domain.repositories

import com.sms.pipe.data.models.ConversationModel
import com.sms.pipe.utils.Result

interface MessagingRepository {

    fun initialize(token:String)

    suspend fun sendMessage(message:String,channelId :String)

    suspend fun getConversationList():Result<List<ConversationModel>>
}