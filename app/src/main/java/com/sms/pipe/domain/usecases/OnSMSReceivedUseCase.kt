package com.sms.pipe.domain.usecases

import android.util.Log
import com.sms.pipe.data.models.MessageModel
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.models.Packet
import com.sms.pipe.domain.models.PacketSlack
import com.sms.pipe.domain.models.PacketSms
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.view.model.AppletType
import com.sms.pipe.view.model.AppletUi

class OnSMSReceivedUseCase(
    private val messagingRepository: MessagingRepository,
    private val appletRepository: AppletRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(smsReceived: MessageModel) {
        Log.d("OnSMSReceivedUseCase", " check enabled applets")
        val user = userRepository.getUser() ?: return

        appletRepository.getEnabledApplets(user.email).map { applet ->
            applet.match(smsReceived)?.let {
                Log.d("OnSMSReceivedUseCase", "Applet Enabled and match : $applet")
                createPacket(it, smsReceived, user)?.let { packet ->
                    messagingRepository.sendMessage(packet)
                }
            }
        }

    }

    private fun createPacket(
        appletUi: AppletUi,
        messageModel: MessageModel,
        userModel: UserModel
    ): Packet? {
        return when (appletUi.type) {
            AppletType.DEVICE -> {
                PacketSms(
                    "Sms Transfer : "+messageModel.messageBody,
                    appletUi.channelName,
                    from = messageModel.sender
                )
            }
            AppletType.SLACK -> {
                userModel.slack_access_token?.let {
                    PacketSlack("Sms Transfer : "+messageModel.messageBody, appletUi.channelName, it)
                }
            }
            else -> {
                null
            }
        }
    }


}