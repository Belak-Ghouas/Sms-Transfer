package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.UserModel

interface UserLocalDataSource {

    suspend fun save(user: UserModel)

    suspend fun getLoggedUser(): UserModel?

    suspend fun logout(): Boolean

}