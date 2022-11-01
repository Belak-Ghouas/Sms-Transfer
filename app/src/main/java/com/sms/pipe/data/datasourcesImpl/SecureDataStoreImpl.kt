package com.sms.pipe.data.datasourcesImpl

import android.content.SharedPreferences
import com.sms.pipe.data.datasources.SecureDataStore
import com.sms.pipe.data.datasources.UserLocalDataSource

class SecureDataStoreImpl(private val encryptedSharedPreferences : SharedPreferences,private val userLocalDataSource: UserLocalDataSource):SecureDataStore {

    override fun store(key: String, value: String) {
        userLocalDataSource.getUser()?.email?.let {
            encryptedSharedPreferences.edit().putString(key+it,value).apply()
        }
    }

    override fun store(key: String, value: Boolean) {
        userLocalDataSource.getUser()?.email?.let {
        encryptedSharedPreferences.edit().putBoolean(key+it,value).apply()
        }
    }

    override fun getString(key: String): String {
        userLocalDataSource.getUser()?.email?.let {
            return encryptedSharedPreferences.getString(key+it, "") ?: ""
        }
        return ""
    }

    override fun getBoolean(key: String): Boolean {
        userLocalDataSource.getUser()?.email?.let {
            return encryptedSharedPreferences.getBoolean(key+it, false)
        }
        return false
    }
}