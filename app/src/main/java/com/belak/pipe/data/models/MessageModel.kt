package com.belak.pipe.data.models

data class MessageModel(
    var messageBody : String="",
    var sender : Long =-1
){
    override fun toString(): String {
        return "{sender : $sender ,  message body: $messageBody}"
    }
}

fun MessageModel.isEmpty():Boolean{
    return messageBody.isEmpty() &&  sender == -1L
}

