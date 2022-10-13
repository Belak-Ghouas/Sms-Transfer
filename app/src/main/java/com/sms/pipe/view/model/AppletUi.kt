package com.sms.pipe.view.model

data class AppletUi(
    var id :Long = 0,
    var appletName:String,
    var creationDate:String = "",
    var channelName: String = "otp_paradise",
    var filters: List<AppletFilterType> = listOf(),
    var relation: AppletFilterRelation? = null,
    var isEnabled : Boolean
)