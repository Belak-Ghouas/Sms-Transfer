package com.sms.pipe.di

import com.sms.pipe.view.addApplet.CreateFilterViewModel
import com.sms.pipe.view.home.HomeViewModel
import com.sms.pipe.view.notifications.NotificationsViewModel
import com.sms.pipe.view.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val homeModules = module {
    viewModel { HomeViewModel() }
}

val vmNotificationModules = module {
    viewModel { NotificationsViewModel() }
}

val profileModules = module {
    viewModel { ProfileViewModel(getLoggedUserUseCase = get(), deleteAccountUseCase = get()) }
}

val vmCreateFilterModules = module {
    viewModel { CreateFilterViewModel() }
}
