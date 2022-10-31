package com.sms.pipe.data.datasources

interface SecureDataStore {

    fun store(key: String, value: String)
    fun store(key: String, value: Boolean)
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
}
