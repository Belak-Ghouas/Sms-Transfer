package com.sms.pipe.data

import com.sms.pipe.data.models.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("/mobile/login")
    suspend fun login(@Field("email") email:String , @Field("password") password:String): Response<UserLoginResponse>

}