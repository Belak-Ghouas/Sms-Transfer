package com.sms.pipe.view.model

interface AppletFilter {
    fun filterBySender(sender:String):Boolean
    fun filterByContent(content:String):Boolean
}