package com.sms.pipe.view.model

import android.os.Parcelable
import com.sms.pipe.data.models.MessageModel

import kotlinx.parcelize.Parcelize

@Parcelize
data class AppletUi(
    var id :Long = 0,
    var appletName:String,
    var creationDate:String = "",
    var channelName: String = "",
    var filters: List<AppletFilter> = listOf(),
    var relation: AppletFilterRelation? = null,
    var userId : String,
    var isEnabled : Boolean,
    var type :AppletType):Parcelable{
    fun match(messageModel: MessageModel):AppletUi?{
        filters.forEach{
            if(it.match(messageModel)) return this
        }
        return null
    }
}