package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository

class InitMessagingUseCase(private val userRepository: UserRepository , private val messagingRepository: MessagingRepository ) {
    suspend operator fun invoke(){
            userRepository.getLoggedUser()?.slack_access_token?.let {
                messagingRepository.initialize(it)
            }
    }
}