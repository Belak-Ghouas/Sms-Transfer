package com.sms.pipe.data.datasourcesImpl

import com.sms.pipe.data.datasources.AppletDataSource
import com.sms.pipe.data.db.dao.AppletDao
import com.sms.pipe.data.db.entity.AppletEntity
import com.sms.pipe.view.model.AppletUi

class AppletDataSourceImpl(private val appletDao: AppletDao): AppletDataSource {
    override suspend fun storeApplet(applet: AppletUi):Boolean {
      return appletDao.insert(applet.toEntity())>-1
    }

    override suspend fun getApplet(id: Long): AppletUi {
        return appletDao.getApplet(id).toModel()
    }

    override suspend fun getAllApplet(): List<AppletUi> {
        return appletDao.getAllApplet().map { it.toModel() }
    }




    private fun AppletUi.toEntity():AppletEntity{
        return AppletEntity(
            appletName = this.appletName,
            creationDate = this.creationDate,
            channelName = this.channelName,
            filters = this.filters,
            isEnabled = this.isEnabled
        )
    }

    private fun AppletEntity.toModel():AppletUi{
        return AppletUi(
            id = this.id,
            appletName = this.appletName,
            creationDate = this.creationDate,
            channelName = this.channelName,
            filters = this.filters,
            isEnabled = this.isEnabled
        )
    }
}