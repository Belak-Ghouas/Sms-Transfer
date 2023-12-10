package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.utils.Result

class DeleteAccountUseCase(
    private val userRepository: UserRepository,
    private val getUserTokenUseCase: GetUserTokenUseCase
) {

    suspend operator fun invoke(): Result<Unit> {
        val token = getUserTokenUseCase()
        return userRepository.deleteAccount(token)
    }
}