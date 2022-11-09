package com.sms.pipe.domain.usecases

import android.util.Log
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository

class InitMessagingUseCase(private val userRepository: UserRepository , private val messagingRepository: MessagingRepository ) {
    suspend operator fun invoke(): Boolean {
        var result = false
        userRepository.getUser()?.slack_access_token?.let {
                  result = messagingRepository.initialize(it)
                  Log.d("InitMessagingUseCase","Initialization is done with this result  $result")
              }?: kotlin.run {
                  result = false
                  Log.e("Init Messaging","Failed to initMessaging due to slack token is null")
              }

        return result
    }
}