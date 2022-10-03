package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.MessagingDataSource
import com.sms.pipe.data.models.ConversationModel
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.utils.Result

class MessagingRepositoryImpl(private val messagingDataSource: MessagingDataSource) :MessagingRepository{
    override fun initialize(token: String) = messagingDataSource.initialize(token)

    override suspend fun sendMessage(message: String, channelId: String) = messagingDataSource.sendMessage(message,channelId)

    override suspend fun getConversationList(): Result<List<ConversationModel>> = messagingDataSource.getConversationList()
}