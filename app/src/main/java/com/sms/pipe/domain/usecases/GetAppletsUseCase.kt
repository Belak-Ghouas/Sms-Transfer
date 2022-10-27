package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.flow.Flow

class GetAppletsUseCase(private val appletRepository: AppletRepository) {

    suspend  operator fun  invoke(): Flow<List<AppletUi>> {
        return appletRepository.getAllApplet()
    }
}