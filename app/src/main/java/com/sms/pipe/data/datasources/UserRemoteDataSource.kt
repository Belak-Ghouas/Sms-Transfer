package com.sms.pipe.data.datasources

import com.sms.pipe.data.models.RefreshTokenResponse
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.utils.Result

interface UserRemoteDataSource {
    suspend fun login(email: String, password: String): Result<UserModel>
    suspend fun signUp(email: String, username: String, password: String): Result<Unit>
    suspend fun save(user: UserModel)
    suspend fun refreshData(token: String): Result<UserModel>
    suspend fun deleteAccount(token: String): Result<Unit>
    // suspend fun refreshToken(refresh_token :String) : Result<RefreshTokenResponse>
}
