package com.sms.pipe.domain.models


interface Packet{
    val to: String
    val content:String
}

data class PacketSlack(override val content :String, override val to:String, val token :String) : Packet {
    override fun toString(): String {
        return "Slack packet send message : $content to channel : $to"
    }
}


class PacketSms(override val content :String, override val to:String, val from:String) : Packet {

}

class PacketEmail(override val content :String, override val to:String) : Packet {

}