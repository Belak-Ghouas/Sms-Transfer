package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_TERMS_CONDITIONS

class OnTermsAcceptedUseCase(private val dataStoreRepository: SecureDataStoreRepository) {

    operator fun invoke(){
        dataStoreRepository.store(KEY_TERMS_CONDITIONS,true)
    }
}
