package com.sms.pipe

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


/**
 * used by gradle to launch the AndroidRunner with our test application
 */
class RoboTestRunner : AndroidJUnitRunner() {

    override fun newApplication(
        classLoader: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(classLoader, TestApplication::class.java.name, context)
    }
}