package com.sms.pipe.data

import com.sms.pipe.data.models.RefreshTokenResponse
import com.sms.pipe.data.models.UserLoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("/mobile/login")
    suspend fun login(@Field("email") email:String , @Field("password") password:String): Response<UserLoginResponse>

    @GET("/mobile/user")
    suspend fun refreshData(@Header("Authorization") authHeader:String) : Response<UserLoginResponse>


    @FormUrlEncoded
    @POST("/api/auth/register")
    suspend fun signUp(@Field("email") email:String , @Field("password") password:String ,@Field("username") username:String): Response<Unit>

    @FormUrlEncoded
    @POST("/api/auth/refresh")
    suspend fun refreshToken(@Field("refresh_token") refresh:String ) : Response<RefreshTokenResponse>
}