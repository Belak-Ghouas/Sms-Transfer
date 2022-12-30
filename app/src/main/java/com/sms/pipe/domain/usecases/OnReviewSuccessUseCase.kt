package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_IN_APP_REVIEW
import com.sms.pipe.utils.WEEK_IN_MILLIS
import java.util.*

class OnReviewSuccessUseCase (private val dataStoreRepository: SecureDataStoreRepository){

    operator fun invoke(){
        val nextTime= (Date().time + WEEK_IN_MILLIS*8).toString()
        dataStoreRepository.store(KEY_IN_APP_REVIEW,nextTime)
    }
}
