package com.sms.pipe.view.model

import com.sms.pipe.data.models.MessageModel

interface AppletFilter {
    fun match(messageModel:MessageModel):Boolean
}