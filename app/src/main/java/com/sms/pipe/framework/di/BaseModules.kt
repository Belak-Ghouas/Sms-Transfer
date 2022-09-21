package com.sms.pipe.framework.di

import com.sms.pipe.SampleApplication.Companion.applicationScope
import com.sms.pipe.data.datasources.MessagingDataSource
import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.repositoriesImpl.MessagingRepositoryImpl
import com.sms.pipe.data.repositoriesImpl.UserRepositoryImpl
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.framework.ApiClient
import com.sms.pipe.framework.ApiInterface
import com.sms.pipe.framework.datasourcesImpl.MessagingDataSourceImpl
import com.sms.pipe.framework.datasourcesImpl.UserLocalDataSourceImpl
import com.sms.pipe.framework.datasourcesImpl.UserRemoteDataSourceImpl
import com.sms.pipe.framework.db.AppDataBase
import com.sms.pipe.utils.PhoneUtils
import com.sms.pipe.view.viewmodels.BaseActivityViewModel
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module



val baseDataModules = module {
    single { AppDataBase.getInstance(androidApplication()).userDao()  }
    single { PhoneUtils(androidApplication()) }
    single <ApiInterface> { ApiClient.getApiClient().create(ApiInterface::class.java) }
    single { applicationScope }
    single <UserRemoteDataSource>{ UserRemoteDataSourceImpl(userApi = get(), phoneUtils = get())  }
    single <UserLocalDataSource>  { UserLocalDataSourceImpl(userDao = get())  }
    factory <UserRepository>{ UserRepositoryImpl(userRemoteDataSource = get(), userLocalDataSource = get() ) }

    single <MessagingDataSource>{ MessagingDataSourceImpl() }
    factory <MessagingRepository>{ MessagingRepositoryImpl(messagingDataSource = get()) }

}

val baseDomainModules = module {
    factory { GetLoggedUserUseCase(userRepository = get()) }
    factory { SendMessageUseCase(messagingRepository = get()) }
    factory { InitMessagingUseCase(userRepository = get(), messagingRepository = get()) }
}
