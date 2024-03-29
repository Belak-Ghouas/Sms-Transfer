package com.sms.pipe.view.model

import com.sms.pipe.data.models.MessageModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class AppletFilterSender(var value: String) : AppletFilter {
    override fun match(messageModel: MessageModel): Boolean {
        return messageModel.sender.equals(value, true)
    }

    override fun toString(): String {
        return "Filter on Sender"
    }
}

@Parcelize
data class AppletFilterContent(var value: String) : AppletFilter {

    override fun match(messageModel: MessageModel): Boolean {
        return messageModel.messageBody.contains(value, true)
    }

    override fun toString(): String {
        return "Filter on Content"
    }
}

enum class AppletFilterRelation {
    AND,
    OR
}