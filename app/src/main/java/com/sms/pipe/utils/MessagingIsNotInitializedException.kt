package com.sms.pipe.utils

class MessagingIsNotInitializedException : Exception() {

    override val message: String
        get() = super.message + "Messaging is not initialized"
}