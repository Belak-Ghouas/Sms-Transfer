package com.sms.pipe.data.datasources

import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.flow.Flow

interface AppletDataSource {

    suspend fun storeApplet(applet: AppletUi): Boolean

    suspend fun getApplet(id: Long): AppletUi

    suspend fun getAllApplet(): Flow<List<AppletUi>>

    suspend fun getEnabledApplets(): List<AppletUi>

    suspend fun deleteApplet(id: Long): Boolean

}