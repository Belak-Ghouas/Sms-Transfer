package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.utils.Result

interface UserRemoteDataSource{
    suspend fun login(email:String, password:String): Result<UserModel>
    suspend fun save(user: UserModel)
    suspend fun refreshData(token:String) : Result<UserModel>
}
