package com.sms.pipe.data.datasourcesImpl

import android.content.SharedPreferences
import com.sms.pipe.data.datasources.SecureDataStore

class SecureDataStoreImpl(private val encryptedSharedPreferences : SharedPreferences):SecureDataStore {
    override fun store(key: String, value: String) {
      encryptedSharedPreferences.edit().putString(key,value).apply()
    }

    override fun get(key: String): String {
        return encryptedSharedPreferences.getString(key,"") ?: ""
    }
}