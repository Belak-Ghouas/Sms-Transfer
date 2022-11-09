package com.sms.pipe.domain.usecases

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository

class GetUserSessionUseCase(private val userRepository: UserRepository) {

    operator fun invoke():UserModel?{
       return userRepository.getUserSession()
    }
}
