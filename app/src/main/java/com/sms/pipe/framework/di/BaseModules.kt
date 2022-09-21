package com.sms.pipe.framework.di

import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.repositoriesImpl.UserRepositoryImpl
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.framework.ApiClient
import com.sms.pipe.framework.ApiInterface
import com.sms.pipe.framework.datasourcesImpl.UserLocalDataSourceImpl
import com.sms.pipe.framework.datasourcesImpl.UserRemoteDataSourceImpl
import com.sms.pipe.framework.db.AppDataBase
import com.sms.pipe.utils.PhoneUtils
import com.sms.pipe.view.viewmodels.BaseActivityViewModel
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModules = module {
    viewModel { BaseActivityViewModel() }
    viewModel { BaseFragmentViewModel() }
}


val baseDataModules = module {
    single { AppDataBase.getInstance(androidApplication()).userDao()  }
    single { PhoneUtils(androidApplication()) }
    single <ApiInterface> { ApiClient.getApiClient().create(ApiInterface::class.java) }

    single <UserRemoteDataSource>{ UserRemoteDataSourceImpl(userApi = get(), phoneUtils = get())  }
    single <UserLocalDataSource>  { UserLocalDataSourceImpl(userDao = get())  }
    factory <UserRepository>{ UserRepositoryImpl(userRemoteDataSource = get(), userLocalDataSource = get() ) }
    factory { GetLoggedUserUseCase(userRepository = get()) }
}
