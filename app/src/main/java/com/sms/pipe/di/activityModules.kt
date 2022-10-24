package com.sms.pipe.di

import com.sms.pipe.domain.usecases.GetListChannelsUseCase
import com.sms.pipe.domain.usecases.LoginUseCase
import com.sms.pipe.view.addApplet.CreateAppletViewModel
import com.sms.pipe.view.base.BaseActivityViewModel
import com.sms.pipe.view.base.BaseFragmentViewModel
import com.sms.pipe.view.login.LoginActivityViewModel
import com.sms.pipe.view.splash.SplashScreenActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val vmLoginModule = module{
    viewModel { LoginActivityViewModel(loginUseCase = get()) }
}

val domainLoginModules = module {
    factory { LoginUseCase(userRepository = get()) }
}


val vmSplashModule= module {
    viewModel { SplashScreenActivityViewModel() }
}


val vmMainActivityModule = module {
    viewModel { BaseActivityViewModel() }
}

val vmChooseSlackModule = module {
    viewModel { BaseFragmentViewModel() }
}

val vmCreateAppletModule = module {
    viewModel { CreateAppletViewModel(getListChannelsUseCase = get() , storeAppletUseCase = get()) }
    factory { GetListChannelsUseCase(messagingRepository = get()) }
}