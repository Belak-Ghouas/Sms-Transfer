package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.UserRepository

class LogoutUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke():Boolean{
       return userRepository.logout()
    }
}