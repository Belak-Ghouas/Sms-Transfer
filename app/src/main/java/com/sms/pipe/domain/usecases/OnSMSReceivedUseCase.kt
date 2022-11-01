package com.sms.pipe.domain.usecases

import android.util.Log
import com.sms.pipe.data.models.MessageModel
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository

class OnSMSReceivedUseCase(private val messagingRepository: MessagingRepository,
                           private val appletRepository: AppletRepository,
                           private val userRepository: UserRepository) {

    suspend operator fun invoke(smsReceived:MessageModel){
        Log.d("OnSMSReceivedUseCase"," check enabled applets")
        userRepository.getUser()?.email?.let {
            appletRepository.getEnabledApplets(it).map { applet ->
                if (applet.match(smsReceived)){
                    Log.d("OnSMSReceivedUseCase","Applet Enabled and match : $applet")
                    messagingRepository.sendMessage(smsReceived.messageBody,applet.channelName)
                }
            }
        }

    }


}