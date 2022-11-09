package com.sms.pipe.data.datasourcesImpl

import android.content.SharedPreferences
import com.sms.pipe.data.datasources.SecureDataStore
import com.sms.pipe.data.datasources.UserLocalDataSource

class SecureDataStoreImpl(
    private val encryptedSharedPreferences: SharedPreferences,
    private val userLocalDataSource: UserLocalDataSource
) : SecureDataStore {

    private val email: String by lazy {
        userLocalDataSource.userSession()?.email ?: ""
    }

    override fun store(key: String, value: String) {
        encryptedSharedPreferences.edit().putString(key + email, value).apply()
    }

    override fun store(key: String, value: Boolean) {
        encryptedSharedPreferences.edit().putBoolean(key + email, value).apply()
    }

    override fun getString(key: String): String {
        return encryptedSharedPreferences.getString(key + email, "") ?: ""
    }

    override fun getBoolean(key: String): Boolean {
        return encryptedSharedPreferences.getBoolean(key + email, false)
    }
}