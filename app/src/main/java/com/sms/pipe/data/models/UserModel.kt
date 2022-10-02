package com.sms.pipe.data.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("email")
    val email: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("slack_access_token")
    val slack_access_token: String?,

    @SerializedName("slack_token_type")
    val slack_token_type: String?,

    @SerializedName("bot_user_id")
    val bot_user_id: String?,

    @SerializedName("slack_app_id")
    val slack_app_id: String?,

    @SerializedName("team")
    val slack_team:SlackTeamModel?,

    var logged:Boolean = true
)