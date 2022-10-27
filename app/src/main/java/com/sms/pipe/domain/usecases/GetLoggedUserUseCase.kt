package com.sms.pipe.domain.usecases

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class GetLoggedUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Flow<UserModel?> {
       return userRepository.getLoggedUser()
    }
}
