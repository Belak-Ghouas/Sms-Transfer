package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.UserRepository

class RefreshUserDataUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(){
        userRepository.refreshData()
    }
}
