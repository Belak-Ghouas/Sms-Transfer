package com.sms.pipe.data.models

import com.google.gson.annotations.SerializedName

data class SlackTeamModel(
    @SerializedName("name")
    val teamName: String?,

    @SerializedName("id")
    val teamId: String?
)