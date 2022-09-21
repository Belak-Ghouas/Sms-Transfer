package com.sms.pipe.data.models

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(

    @SerializedName("user")
    val user : UserModel?,

    @SerializedName("token")
    val token:String?
)