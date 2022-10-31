package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.SecureDataStore
import com.sms.pipe.domain.repositories.SecureDataStoreRepository

class SecureDataStoreRepositoryImpl( private val secureDataStore: SecureDataStore):SecureDataStoreRepository {

    override fun store(key: String, value: String) = secureDataStore.store(key, value)

    override fun store(key: String, value: Boolean) = secureDataStore.store(key, value)

    override fun getString(key: String): String = secureDataStore.getString(key)

    override fun getBoolean(key: String): Boolean = secureDataStore.getBoolean(key)
}