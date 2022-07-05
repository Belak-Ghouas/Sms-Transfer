package com.belak.pipe

import android.app.Application
import android.content.Context
import com.belak.pipe.framework.di.vmModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
       initKoin(this)
    }

    private fun initKoin(context: Context) {
        startKoin {
            androidLogger(Level.INFO)
            androidContext(context)
        }
        loadKoinModules(vmModules)
    }
}