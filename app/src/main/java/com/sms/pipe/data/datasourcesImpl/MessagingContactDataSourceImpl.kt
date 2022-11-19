package com.sms.pipe.data.datasourcesImpl

import android.content.Context
import android.telephony.SmsManager
import com.sms.pipe.data.datasources.MessagingContactDataSource
import com.sms.pipe.domain.models.PacketSms


class MessagingContactDataSourceImpl(private val context: Context) : MessagingContactDataSource {
    override fun sendMessage(packetSms: PacketSms) {

        try {
            val smsManager: SmsManager = context.getSystemService(SmsManager::class.java)
            smsManager.sendTextMessage(
                packetSms.to,null,packetSms.content,null,null
            )

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

}
