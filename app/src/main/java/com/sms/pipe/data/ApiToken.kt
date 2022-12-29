package com.sms.pipe.data

import com.sms.pipe.data.models.RefreshTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiToken {

    @FormUrlEncoded
    @POST("/mobile/refresh_token")
    suspend fun refreshToken(@Field("refresh_token") refresh:String ) : Response<RefreshTokenResponse>
}