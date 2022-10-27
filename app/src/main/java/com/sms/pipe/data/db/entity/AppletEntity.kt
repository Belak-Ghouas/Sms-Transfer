package com.sms.pipe.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity
data class AppletEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "applet_id") val applet_id: Long = 0,

    @ColumnInfo(name = "appletName") val appletName:String,

    @ColumnInfo(name = "creation_date") val creationDate:String,

    @ColumnInfo(name = "channel_name") val channelName: String,

    @ColumnInfo(name = "filters") val filters: List<AppletFilterEntity> = listOf(),

    @ColumnInfo(name = "isEnabled") val isEnabled :Boolean
)


object Converters {
    @TypeConverter
    fun fromString(value: String): List<AppletFilterEntity> {
        val listType = object: TypeToken<ArrayList<AppletFilterEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list:List<AppletFilterEntity>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}