package com.sms.pipe.view.model

import com.sms.pipe.data.models.MessageModel

data class AppletUi(
    var id :Long = 0,
    var appletName:String,
    var creationDate:String = "",
    var channelName: String = "",
    var filters: List<AppletFilter> = listOf(),
    var relation: AppletFilterRelation? = null,
    var userId : String,
    var isEnabled : Boolean,
    var type :AppletType){
    fun match(messageModel: MessageModel):AppletUi?{
        filters.forEach{
            if(it.match(messageModel)) return this
        }
        return null
    }
}