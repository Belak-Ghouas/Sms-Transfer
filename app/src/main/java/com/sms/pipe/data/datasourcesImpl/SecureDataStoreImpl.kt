package com.sms.pipe.data.datasourcesImpl

import android.content.SharedPreferences
import com.sms.pipe.data.datasources.SecureDataStore

class SecureDataStoreImpl(private val encryptedSharedPreferences : SharedPreferences):SecureDataStore {
    override fun store(key: String, value: String) {
      encryptedSharedPreferences.edit().putString(key,value).apply()
    }

    override fun store(key: String, value: Boolean) {
        encryptedSharedPreferences.edit().putBoolean(key,value).apply()
    }

    override fun getString(key: String): String {
        return encryptedSharedPreferences.getString(key,"") ?: ""
    }

    override fun getBoolean(key: String): Boolean {
        return encryptedSharedPreferences.getBoolean(key,false)
    }
}