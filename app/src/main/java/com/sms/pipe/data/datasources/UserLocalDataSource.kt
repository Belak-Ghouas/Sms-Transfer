package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun save(user: UserModel)

    suspend fun getLoggedUser(): Flow<UserModel?>

    suspend fun logout(): Boolean

    fun getUser():UserModel?

}