package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.MessagingRepository

class SendMessageUseCase(private val messagingRepository: MessagingRepository) {

    suspend operator fun invoke(message:String,channelId :String){
        messagingRepository.sendMessage(message,channelId)
    }
}