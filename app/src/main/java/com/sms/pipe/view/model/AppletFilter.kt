package com.sms.pipe.view.model

import android.os.Parcelable
import com.sms.pipe.data.models.MessageModel

interface AppletFilter : Parcelable {
    fun match(messageModel:MessageModel):Boolean
}