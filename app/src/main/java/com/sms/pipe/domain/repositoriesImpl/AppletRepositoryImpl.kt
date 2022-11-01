package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.AppletDataSource
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.flow.Flow

class AppletRepositoryImpl(private val appletDataSource: AppletDataSource):AppletRepository {
    override suspend fun storeApplet(applet: AppletUi):Boolean {
       return appletDataSource.storeApplet(applet)
    }

    override suspend fun getApplet(id: Long): AppletUi {
       return appletDataSource.getApplet(id)
    }

    override suspend fun getAllApplet(username:String): Flow<List<AppletUi>> {
        return appletDataSource.getAllApplet(username)
    }

    override suspend fun getEnabledApplets(username:String): List<AppletUi> {
        return appletDataSource.getEnabledApplets(username)
    }

    override suspend fun deleteApplet(id: Long):Boolean = appletDataSource.deleteApplet(id)
}