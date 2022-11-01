package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.UserRepository

class RefreshUserDataUseCase(private val userRepository: UserRepository, private val getUserTokenUseCase: GetUserTokenUseCase) {

    suspend operator fun invoke(){
        getUserTokenUseCase().also{
            if(it.isNotEmpty()) userRepository.refreshData(it)
        }
    }
}
