package com.sms.pipe.di

import com.sms.pipe.domain.usecases.GetAppletsUseCase
import com.sms.pipe.domain.usecases.LogoutUseCase
import com.sms.pipe.view.addApplet.CreateFilterViewModel
import com.sms.pipe.view.dashboard.DashboardViewModel
import com.sms.pipe.view.home.HomeViewModel
import com.sms.pipe.view.notifications.NotificationsViewModel
import com.sms.pipe.view.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val vmHomeModules = module {
    viewModel { HomeViewModel(sendMessageUseCase = get(), getAppletsUseCase = get(), refreshUserDataUseCase = get()) }
    factory { GetAppletsUseCase(appletRepository = get()) }
}

val vmDashboardModules = module {
    viewModel { DashboardViewModel(refreshUserDataUseCase = get()) }
}

val vmNotificationModules = module {
    viewModel { NotificationsViewModel() }
}

val profileModules = module {
    viewModel { ProfileViewModel(logoutUseCase = get()) }
    factory { LogoutUseCase(userRepository = get()) }
}

val vmCreateFilterModules = module {
    viewModel { CreateFilterViewModel() }
}
