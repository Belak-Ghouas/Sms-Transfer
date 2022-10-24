package com.sms.pipe.view.base

import androidx.lifecycle.ViewModel
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import org.koin.java.KoinJavaComponent.inject

abstract class BaseViewModel : ViewModel() {
    protected val getLoggedUserUseCase : GetLoggedUserUseCase by  inject(GetLoggedUserUseCase::class.java)

}