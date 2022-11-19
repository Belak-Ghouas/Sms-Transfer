package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.domain.models.PacketSlack
import com.sms.pipe.utils.Result

interface MessagingSlackDataSource {

    fun initialize(token:String):Boolean

    suspend fun sendMessage(packetSlack: PacketSlack)

    suspend fun getConversationList(): Result<List<ChannelModel>>
}