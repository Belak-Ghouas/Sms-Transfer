package com.sms.pipe

import android.app.Application
import android.content.Context
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.di.baseDataModules
import com.sms.pipe.di.baseDomainModules
import com.sms.pipe.domain.usecases.IsAlreadyOnboardedUseCase
import com.sms.pipe.domain.usecases.StoreAlreadyOnBoarderUseCase
import com.sms.pipe.domain.usecases.UpdateStepsUseCase
import com.sms.pipe.view.model.Step
import com.sms.pipe.view.model.StepStatus
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SampleApplication : Application() {

    private val initMessagingUseCase :InitMessagingUseCase by inject()
    private val isAlreadyOnBoarded : IsAlreadyOnboardedUseCase by inject()
    private val updateStepsUseCase : UpdateStepsUseCase by inject()
    private val storeAlreadyOnBoarderUseCase : StoreAlreadyOnBoarderUseCase by inject()

    companion object{
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
        isFirstTime()
    }

    private fun isFirstTime() {
        if(!isAlreadyOnBoarded()){
            storeAlreadyOnBoarderUseCase()
            updateStepsUseCase(
                listOf(
                    Step(0,StepStatus.DONE),
                    Step(1,StepStatus.IN_PROGRESS),
                    Step(2,StepStatus.NOT_DONE)
                )
            )
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