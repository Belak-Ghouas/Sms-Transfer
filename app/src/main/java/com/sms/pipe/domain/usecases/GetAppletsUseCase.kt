package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class GetAppletsUseCase(private val appletRepository: AppletRepository , private val userRepository: UserRepository) {

    suspend  operator fun  invoke(): Flow<List<AppletUi>> {
      return  userRepository.getUser()?.email?.let {
            return appletRepository.getAllApplet(it)
        }?:run{
            emptyFlow()
        }

    }
}