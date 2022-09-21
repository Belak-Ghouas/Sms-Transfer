package com.sms.pipe

import android.app.Application
import android.content.Context
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.framework.di.baseDataModules
import com.sms.pipe.framework.di.baseDomainModules
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SampleApplication : Application() {

    private val initMessagingUseCase :InitMessagingUseCase by inject()
    companion object{
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
        applicationScope.launch {
            initMessagingUseCase()
        }
    }


    private fun initKoin(context: Context) {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(context)
        }
        loadKoinModules(listOf(baseDataModules, baseDomainModules))
    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel()
    }
}