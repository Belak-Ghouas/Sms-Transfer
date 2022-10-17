package com.sms.pipe.domain.repositories

import com.sms.pipe.view.model.AppletUi

interface AppletRepository {
    suspend fun storeApplet(applet: AppletUi):Boolean

    suspend fun getApplet(id:Long): AppletUi

    suspend fun getAllApplet():List<AppletUi>

    suspend fun getEnabledApplets():List<AppletUi>
}