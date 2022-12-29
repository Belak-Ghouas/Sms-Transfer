package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.KEY_IN_APP_REVIEW
import com.sms.pipe.utils.WEEK_IN_MILLIS
import com.sms.pipe.utils.ifNotEmpty
import java.util.*

class NeedToShowInAppReview(private val dataStoreRepository: SecureDataStoreRepository) {

   operator fun invoke(): Boolean {
       dataStoreRepository.getString(KEY_IN_APP_REVIEW).ifNotEmpty {
           return if( Date().time - it.toLong() >=  1000){
               dataStoreRepository.store(KEY_IN_APP_REVIEW,(Date().time+WEEK_IN_MILLIS).toString())
               true
           }else{
               false
           }
        }

       dataStoreRepository.store(KEY_IN_APP_REVIEW,Date().time.toString())

        return false
    }

}
