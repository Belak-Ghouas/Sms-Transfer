package com.sms.pipe.data.models

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse (

    @SerializedName("access_token")
    val access_token : String?,

    @SerializedName("refresh_token")
    val refresh_token : String?

)
