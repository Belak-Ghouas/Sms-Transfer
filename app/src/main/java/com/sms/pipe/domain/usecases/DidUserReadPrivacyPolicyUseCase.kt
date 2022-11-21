package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_TERMS_CONDITIONS

class DidUserReadPrivacyPolicyUseCase(private val storeRepository: SecureDataStoreRepository) {

    operator fun invoke():Boolean{
        return storeRepository.getBoolean(KEY_TERMS_CONDITIONS)
    }
}
