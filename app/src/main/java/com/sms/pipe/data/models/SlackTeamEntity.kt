package com.sms.pipe.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class SlackTeamEntity(
    @ColumnInfo(name = "teamName")
    val teamName: String?,

    @ColumnInfo(name = "teamID")
    val teamId: String?
)