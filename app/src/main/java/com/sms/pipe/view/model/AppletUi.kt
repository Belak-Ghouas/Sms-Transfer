package com.sms.pipe.view.model

import com.sms.pipe.data.models.MessageModel

data class AppletUi(
    var id :Long = 0,
    var appletName:String,
    var creationDate:String = "",
    var channelName: String = "otp_paradise",
    var filters: List<AppletFilter> = listOf(),
    var relation: AppletFilterRelation? = null,
    var userId : String,
    var isEnabled : Boolean){
    fun match(messageModel: MessageModel):Boolean{
        filters.forEach{
            if(it.match(messageModel)) return true
        }
        return false
    }
}