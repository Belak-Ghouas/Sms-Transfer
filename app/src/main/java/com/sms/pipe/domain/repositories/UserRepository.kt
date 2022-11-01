package com.sms.pipe.domain.repositories

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.utils.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun login(email:String , password:String): Result<UserModel>

    suspend fun signUp(email:String,username:String, password:String):Result<Unit>

    suspend fun getLoggedUser(): Flow<UserModel?>

    fun getUser():UserModel?

    suspend fun logout(): Boolean

    suspend fun refreshData(token: String)
}