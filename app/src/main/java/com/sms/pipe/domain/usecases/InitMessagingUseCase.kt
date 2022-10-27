package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository

class InitMessagingUseCase(private val userRepository: UserRepository , private val messagingRepository: MessagingRepository ) {
    suspend operator fun invoke(): Boolean {
        var result = false
          userRepository.getLoggedUser().collect{user->
              user?.slack_access_token?.let {
                  result = messagingRepository.initialize(it)
              }?: kotlin.run {
                  result = false
              }
          }
        return result
    }
}