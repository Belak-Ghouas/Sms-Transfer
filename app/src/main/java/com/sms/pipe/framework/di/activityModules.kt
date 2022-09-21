package com.sms.pipe.framework.di

import com.sms.pipe.domain.usecases.LoginUseCase
import com.sms.pipe.view.viewmodels.BaseActivityViewModel
import com.sms.pipe.view.viewmodels.LoginActivityViewModel
import com.sms.pipe.view.viewmodels.SplashScreenActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val vmLoginModule = module{
    viewModel { LoginActivityViewModel(loginUseCase = get()) }
}

val domainLoginModules = module {
    factory { LoginUseCase(userRepository = get()) }
}


val vmSplashModule= module {
    viewModel { SplashScreenActivityViewModel(getLoggedUserUseCase = get()) }
}

val vmScrollingModule = module {
    viewModel { BaseActivityViewModel() }
}

val vmMainActivityModule = module {
    viewModel { BaseActivityViewModel() }
}