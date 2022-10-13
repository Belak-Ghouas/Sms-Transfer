package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.view.model.AppletUi

class GetAppletsUseCase(private val appletRepository: AppletRepository) {

    suspend  operator fun  invoke(): List<AppletUi> {
        return appletRepository.getAllApplet()
    }
}