package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.AppletDataSource
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.view.model.AppletUi

class AppletRepositoryImpl(private val appletDataSource: AppletDataSource):AppletRepository {
    override suspend fun storeApplet(applet: AppletUi):Boolean {
       return appletDataSource.storeApplet(applet)
    }

    override suspend fun getApplet(id: Long): AppletUi {
       return appletDataSource.getApplet(id)
    }

    override suspend fun getAllApplet(): List<AppletUi> {
        return appletDataSource.getAllApplet()
    }
}