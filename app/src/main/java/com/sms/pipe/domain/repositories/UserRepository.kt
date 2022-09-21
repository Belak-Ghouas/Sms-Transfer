package com.sms.pipe.domain.repositories

import com.sms.pipe.data.models.UserModel

interface UserRepository {

    suspend fun login(email:String , password:String): UserModel?

    suspend fun getLoggedUser(): UserModel?
}