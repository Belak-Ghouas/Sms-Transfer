package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.AppletRepository

class DeleteAppletUseCase(private val appletRepository: AppletRepository) {

    suspend operator fun invoke(id:Long):Boolean{
       return appletRepository.deleteApplet(id)
    }
}
