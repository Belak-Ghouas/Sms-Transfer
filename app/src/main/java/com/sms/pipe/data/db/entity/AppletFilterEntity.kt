package com.sms.pipe.data.db.entity

import com.sms.pipe.data.datasourcesImpl.AppletDataSourceImpl


data class AppletFilterEntity(
    val appletFilterType:AppletDataSourceImpl.AppletFilterType,
    val value :String
)
