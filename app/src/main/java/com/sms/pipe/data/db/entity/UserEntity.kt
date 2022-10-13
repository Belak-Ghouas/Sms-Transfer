package com.sms.pipe.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "email") val email: String,

    @ColumnInfo(name = "username") val username: String,

    @ColumnInfo(name = "slack_access_token") val slack_access_token: String,

    @ColumnInfo(name = "slack_token_type") val slack_token_type: String,

    @ColumnInfo(name = "bot_user_id") val bot_user_id: String,

    @ColumnInfo(name = "slack_app_id") val slack_app_id: String,

    @ColumnInfo(name = "teamName") val teamName: String?,

    @ColumnInfo(name = "teamID") val teamId: String?,

    @ColumnInfo(name = "logged") val logged:Boolean
)