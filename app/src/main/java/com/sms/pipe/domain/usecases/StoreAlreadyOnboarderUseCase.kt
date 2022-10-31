package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_ALREADY_ONBOARDED

class StoreAlreadyOnBoarderUseCase(private val dataStoreRepository: SecureDataStoreRepository) {

    operator fun invoke(){
        dataStoreRepository.store(KEY_ALREADY_ONBOARDED,true)
    }
}