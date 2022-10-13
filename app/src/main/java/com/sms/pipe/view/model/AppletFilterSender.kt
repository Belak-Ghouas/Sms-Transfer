package com.sms.pipe.view.model

data class AppletFilterSender(val senderNumber:String):AppletFilter {

    override fun filterBySender(sender:String): Boolean {
        return senderNumber.equals(sender,true)
    }

    override fun filterByContent(content:String): Boolean {
       return false
    }
}

enum class AppletFilterType{
    BY_SENDER,
    BY_CONTENT
}

enum class AppletFilterRelation{
    AND,
    OR
}