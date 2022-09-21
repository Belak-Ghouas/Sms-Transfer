package com.sms.pipe.framework.di

import com.sms.pipe.view.activities.ui.dashboard.DashboardViewModel
import com.sms.pipe.view.activities.ui.home.HomeViewModel
import com.sms.pipe.view.activities.ui.notifications.NotificationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val vmHomeModules = module {
    viewModel { HomeViewModel() }
}

val vmDashboardModules = module {
    viewModel { DashboardViewModel() }
}

val vmNotificationModules = module {
    viewModel { NotificationsViewModel() }
}
