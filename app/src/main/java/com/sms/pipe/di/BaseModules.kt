package com.sms.pipe.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.sms.pipe.SampleApplication.Companion.applicationScope
import com.sms.pipe.data.ApiClient
import com.sms.pipe.domain.repositoriesImpl.MessagingRepositoryImpl
import com.sms.pipe.domain.repositoriesImpl.UserRepositoryImpl
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.data.ApiInterface
import com.sms.pipe.data.datasources.*
import com.sms.pipe.data.datasourcesImpl.*
import com.sms.pipe.data.db.AppDataBase
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.domain.repositoriesImpl.AppletRepositoryImpl
import com.sms.pipe.domain.usecases.*
import com.sms.pipe.utils.PhoneUtils
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val baseDataModules = module {
    single { AppDataBase.getInstance(androidApplication()).userDao()  }
    single { AppDataBase.getInstance(androidApplication()).appletDao()  }
    single { PhoneUtils(androidApplication()) }
    single <ApiInterface> { ApiClient.getApiClient().create(ApiInterface::class.java) }
    single { applicationScope }
    single <UserRemoteDataSource>{ UserRemoteDataSourceImpl(userApi = get(), phoneUtils = get())  }
    single <UserLocalDataSource>  { UserLocalDataSourceImpl(userDao = get())  }
    single <SecureDataStore>{ SecureDataStoreImpl( encryptedSharedPreferences = getEncrypted(androidContext()))  }
    factory <UserRepository>{ UserRepositoryImpl(userRemoteDataSource = get(), userLocalDataSource = get() , secureDataStore = get() ) }

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
    factory { OnSMSReceivedUseCase(messagingRepository = get(), appletRepository = get()) }
    factory { RefreshUserDataUseCase(userRepository = get()) }
}

fun getEncrypted(context: Context): SharedPreferences {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

   return EncryptedSharedPreferences.create(
        "encrypted_preferences", // fileName
        masterKeyAlias, // masterKeyAlias
        context , // context
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, // prefKeyEncryptionScheme
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM // prefvalueEncryptionScheme
    )
}