package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.view.model.AppletUi

class StoreAppletUseCase(private val appletRepository: AppletRepository) {

    suspend operator fun invoke(applet:AppletUi):Boolean{
       return appletRepository.storeApplet(applet)
    }
}
