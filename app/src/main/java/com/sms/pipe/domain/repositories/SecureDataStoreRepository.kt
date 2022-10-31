package com.sms.pipe.domain.repositories

interface SecureDataStoreRepository {

    fun store(key: String, value: String)
    fun store(key: String, value: Boolean)
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
}