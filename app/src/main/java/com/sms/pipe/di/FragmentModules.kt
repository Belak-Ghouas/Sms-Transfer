package com.sms.pipe.di

import com.sms.pipe.domain.usecases.LogoutUseCase
import com.sms.pipe.view.dashboard.DashboardViewModel
import com.sms.pipe.view.home.HomeViewModel
import com.sms.pipe.view.notifications.NotificationsViewModel
import com.sms.pipe.view.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val vmHomeModules = module {
    viewModel { HomeViewModel(sendMessageUseCase = get(), messagingRepository = get(), getLoggedUserUseCase = get()) }
}

val vmDashboardModules = module {
    viewModel { DashboardViewModel() }
}

val vmNotificationModules = module {
    viewModel { NotificationsViewModel() }
}

val profileModules = module {
    viewModel { ProfileViewModel(logoutUseCase = get()) }
    factory { LogoutUseCase(userRepository = get()) }
}
