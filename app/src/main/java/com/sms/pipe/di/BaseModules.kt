package com.sms.pipe.di

import com.sms.pipe.SampleApplication.Companion.applicationScope
import com.sms.pipe.data.ApiClient
import com.sms.pipe.data.datasources.MessagingDataSource
import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.domain.repositoriesImpl.MessagingRepositoryImpl
import com.sms.pipe.domain.repositoriesImpl.UserRepositoryImpl
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.data.ApiInterface
import com.sms.pipe.data.datasources.AppletDataSource
import com.sms.pipe.data.datasourcesImpl.AppletDataSourceImpl
import com.sms.pipe.data.datasourcesImpl.MessagingDataSourceImpl
import com.sms.pipe.data.datasourcesImpl.UserLocalDataSourceImpl
import com.sms.pipe.data.datasourcesImpl.UserRemoteDataSourceImpl
import com.sms.pipe.data.db.AppDataBase
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.domain.repositoriesImpl.AppletRepositoryImpl
import com.sms.pipe.domain.usecases.StoreAppletUseCase
import com.sms.pipe.utils.PhoneUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module



val baseDataModules = module {
    single { AppDataBase.getInstance(androidApplication()).userDao()  }
    single { AppDataBase.getInstance(androidApplication()).appletDao()  }
    single { PhoneUtils(androidApplication()) }
    single <ApiInterface> { ApiClient.getApiClient().create(ApiInterface::class.java) }
    single { applicationScope }
    single <UserRemoteDataSource>{ UserRemoteDataSourceImpl(userApi = get(), phoneUtils = get())  }
    single <UserLocalDataSource>  { UserLocalDataSourceImpl(userDao = get())  }
    factory <UserRepository>{ UserRepositoryImpl(userRemoteDataSource = get(), userLocalDataSource = get() ) }

    single <MessagingDataSource>{ MessagingDataSourceImpl() }
    factory <MessagingRepository>{ MessagingRepositoryImpl(messagingDataSource = get()) }
    single <AppletDataSource>{AppletDataSourceImpl(appletDao = get())  }
    factory <AppletRepository> { AppletRepositoryImpl(appletDataSource = get()) }

}

val baseDomainModules = module {
    factory { GetLoggedUserUseCase(userRepository = get()) }
    factory { SendMessageUseCase(messagingRepository = get()) }
    factory { InitMessagingUseCase(userRepository = get(), messagingRepository = get()) }
    factory { StoreAppletUseCase(appletRepository = get()) }
}
