package com.sms.pipe.data.datasources

import com.sms.pipe.view.model.AppletUi

interface AppletDataSource {

    suspend fun storeApplet(applet:AppletUi):Boolean

    suspend fun getApplet(id:Long):AppletUi

    suspend fun getAllApplet():List<AppletUi>
}