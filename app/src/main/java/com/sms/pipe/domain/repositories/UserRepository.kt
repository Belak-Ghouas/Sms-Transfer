package com.sms.pipe.domain.repositories

import com.sms.pipe.data.models.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email:String , password:String): UserModel?

    suspend fun getLoggedUser(): Flow<UserModel?>

    suspend fun logout(): Boolean

    suspend fun refreshData()
}