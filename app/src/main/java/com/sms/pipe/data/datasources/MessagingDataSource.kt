package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.ConversationModel

interface MessagingDataSource {

    fun initialize(token:String)

    suspend fun sendMessage(message:String,channelId :String)

    suspend fun getConversationList():List<ConversationModel>
}