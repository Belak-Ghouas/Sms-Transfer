package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_TOKEN

class GetUserTokenUseCase(private val dataStoreRepository: SecureDataStoreRepository) {

    operator fun invoke():String{
        return dataStoreRepository.getString(KEY_TOKEN)
    }

}
