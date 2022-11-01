package com.sms.pipe.domain.repositories

import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.utils.Result

interface MessagingRepository {

    fun initialize(token:String):Boolean

    suspend fun sendMessage(message:String,channelId :String)

    suspend fun getListChannels():Result<List<ChannelModel>>
}