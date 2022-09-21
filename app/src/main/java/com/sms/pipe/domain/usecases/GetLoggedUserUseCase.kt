package com.sms.pipe.domain.usecases

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository

class GetLoggedUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): UserModel?{
       return userRepository.getLoggedUser()
    }
}
