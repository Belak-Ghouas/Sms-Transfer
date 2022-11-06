package com.sms.pipe.di

import com.sms.pipe.domain.usecases.*
import com.sms.pipe.view.MainActivityViewModel
import com.sms.pipe.view.addApplet.CreateAppletViewModel
import com.sms.pipe.view.login.LoginActivityViewModel
import com.sms.pipe.view.splash.SplashScreenActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val loginModule = module {
    viewModel {
        LoginActivityViewModel(
            loginUseCase = get(),
            initMessagingUseCase = get(),
            signUpUseCase = get(),
            isAlreadyOnboardedUseCase = get(),
            storeAlreadyOnBoarderUseCase = get(),
            updateStepsUseCase = get(),
            didAcceptTerms = get(),
            onTermsAcceptedUseCase = get()
        )
    }
    factory { LoginUseCase(userRepository = get()) }
    factory { SignUpUseCase(userRepository = get()) }
    factory { DidAcceptTermsUseCase(storeRepository = get()) }
    factory { OnTermsAcceptedUseCase(dataStoreRepository = get()) }
}


val vmSplashModule = module {
    viewModel { SplashScreenActivityViewModel(getLoggedUserUseCase = get()) }
}


val mainActivityModule = module {
    viewModel {
        MainActivityViewModel(
            getLoggedUserUseCase = get(),
            refreshUserDataUseCase = get(),
            deleteAppletUseCase = get(),
            getAppletsUseCase = get(),
            getUserToken = get()
        )
    }

    factory { DeleteAppletUseCase(appletRepository = get()) }
    factory { GetAppletsUseCase(appletRepository = get(), userRepository = get()) }
    factory { GetUserTokenUseCase(dataStoreRepository = get()) }
}


val createAppletModule = module {
    viewModel {
        CreateAppletViewModel(
            getListChannelsUseCase = get(),
            storeAppletUseCase = get(),
            getUserUseCase = get()
        )
    }
    factory { GetListChannelsUseCase(messagingRepository = get()) }
    factory { GetUserUseCase(userRepository = get()) }
}