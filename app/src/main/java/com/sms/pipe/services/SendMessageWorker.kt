package com.sms.pipe.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SendMessageWorker (appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

        override fun doWork(): Result {
            return try {

                Result.Success()
            } catch (e: Exception) {
                Result.failure()
            }
        }

}