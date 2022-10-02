package com.sms.pipe.framework.di

import com.sms.pipe.view.activities.dashboard.DashboardViewModel
import com.sms.pipe.view.activities.home.HomeViewModel
import com.sms.pipe.view.activities.notifications.NotificationsViewModel
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
