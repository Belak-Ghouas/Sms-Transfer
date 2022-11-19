package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.MessagingContactDataSource
import com.sms.pipe.data.datasources.MessagingSlackDataSource
import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.domain.models.Packet
import com.sms.pipe.domain.models.PacketSlack
import com.sms.pipe.domain.models.PacketSms
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.utils.Result

class MessagingRepositoryImpl(
    private val messagingSlackDataSource: MessagingSlackDataSource,
    private val messagingContact: MessagingContactDataSource
) : MessagingRepository {

    override fun initialize(token: String) = messagingSlackDataSource.initialize(token)


    override suspend fun sendMessage(packet: Packet) {
        when (packet) {
            is PacketSlack -> messagingSlackDataSource.sendMessage(packet)
            is PacketSms -> messagingContact.sendMessage(packet)
        }
    }

    override suspend fun getListChannels(): Result<List<ChannelModel>> =
        messagingSlackDataSource.getConversationList()
}