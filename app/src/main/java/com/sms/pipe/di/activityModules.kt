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
            updateStepsUseCase = get()
        )
    }
    factory { LoginUseCase(userRepository = get()) }
    factory { SignUpUseCase(userRepository = get()) }
}


val vmSplashModule = module {
    viewModel { SplashScreenActivityViewModel(getUserOnceUseCase = get()) }
}


val mainActivityModule = module {
    viewModel {
        MainActivityViewModel(
            getLoggedUserUseCase = get(),
            refreshUserDataUseCase = get(),
            deleteAppletUseCase = get(),
            getAppletsUseCase = get(),
            getUserToken = get(),
            didUserReadPrivacyPolicyUseCase = get(),
            onTermsAcceptedUseCase = get() ,
            needToShowInAppReview = get(),
            onReviewSuccessUseCase = get()
        )
    }

    factory { DeleteAppletUseCase(appletRepository = get()) }
    factory { GetAppletsUseCase(appletRepository = get(), userRepository = get()) }
    factory { GetUserTokenUseCase(dataStoreRepository = get()) }
    factory { DidUserReadPrivacyPolicyUseCase(storeRepository = get()) }
    factory { OnTermsAcceptedUseCase(dataStoreRepository = get()) }
    factory { NeedToShowInAppReview(dataStoreRepository = get()) }
    factory { OnReviewSuccessUseCase(dataStoreRepository = get()) }
}


val createAppletModule = module {
    viewModel {
        CreateAppletViewModel(
            getListChannelsUseCase = get(),
            storeAppletUseCase = get(),
            getUserSessionUseCase = get()
        )
    }
    factory { GetListChannelsUseCase(messagingRepository = get()) }
    factory { GetUserSessionUseCase(userRepository = get()) }
}