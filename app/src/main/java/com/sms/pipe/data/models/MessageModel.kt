package com.sms.pipe.data.models

data class MessageModel(
    var messageBody : String="",
    var sender : String =""
){
    override fun toString(): String {
        return "{sender : $sender ,  message body: $messageBody}"
    }
}

fun MessageModel.isEmpty():Boolean{
    return messageBody.isEmpty() &&  sender.isEmpty()
}

