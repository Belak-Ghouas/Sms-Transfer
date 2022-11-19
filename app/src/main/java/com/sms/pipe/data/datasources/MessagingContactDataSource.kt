package com.sms.pipe.data.datasources

import com.sms.pipe.domain.models.PacketSms

interface MessagingContactDataSource {

    fun sendMessage(packetSms: PacketSms)
}
