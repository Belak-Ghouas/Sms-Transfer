package com.sms.pipe

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sms.pipe.di.baseDataModules
import com.sms.pipe.di.baseDomainModules
import com.sms.pipe.di.viewModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SmsTransferApp : Application() {

    companion object{
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().apply {
            setCustomKey(
                "VERSION_CODE",
                BuildConfig.VERSION_CODE
            )
        }
        initKoin(this)
    }


    private fun initKoin(context: Context) {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(context)
        }
        loadKoinModules(listOf(viewModule,baseDataModules, baseDomainModules))
    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel()
    }
}