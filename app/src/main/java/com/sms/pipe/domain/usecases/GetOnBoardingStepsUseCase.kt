package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.GsonHelper.deserialize
import com.sms.pipe.utils.KEY_STEPS
import com.sms.pipe.view.model.Step

class GetOnBoardingStepsUseCase(private val dataStoreRepository: SecureDataStoreRepository) {

    operator fun invoke(): List<Step> {
       return dataStoreRepository.getString(KEY_STEPS).ifEmpty {
            return listOf()
        }.deserialize()
    }
}
