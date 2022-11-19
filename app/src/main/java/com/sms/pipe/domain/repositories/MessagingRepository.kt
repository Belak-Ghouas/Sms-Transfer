package com.sms.pipe.domain.repositories

import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.domain.models.Packet
import com.sms.pipe.utils.Result
import com.sms.pipe.view.model.AppletUi

interface MessagingRepository {

    fun initialize(token:String):Boolean

    suspend fun getListChannels():Result<List<ChannelModel>>

    suspend fun sendMessage(packet : Packet)
}