package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.GsonHelper.toJson
import com.sms.pipe.utils.KEY_STEPS
import com.sms.pipe.view.model.Step

class UpdateStepsUseCase(private val dataStoreRepository: SecureDataStoreRepository){

    operator fun invoke(steps:List<Step>){
        val value:String = steps.toJson()
        dataStoreRepository.store(KEY_STEPS,value)
    }
}