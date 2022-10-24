package com.sms.pipe.data.datasources

interface SecureDataStore {

    fun store(key:String , value:String)
    fun get(key:String):String
}
