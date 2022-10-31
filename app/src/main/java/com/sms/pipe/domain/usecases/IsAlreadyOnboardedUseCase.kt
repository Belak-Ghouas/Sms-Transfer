package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_ALREADY_ONBOARDED

class IsAlreadyOnboardedUseCase(private val dataStoreRepository: SecureDataStoreRepository) {

    operator fun invoke():Boolean = dataStoreRepository.getBoolean(KEY_ALREADY_ONBOARDED)
}