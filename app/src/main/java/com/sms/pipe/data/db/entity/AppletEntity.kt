package com.sms.pipe.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sms.pipe.view.model.AppletFilterType


@Entity
data class AppletEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,

    @ColumnInfo(name = "appletName") val appletName:String,

    @ColumnInfo(name = "creation_date") val creationDate:String ,

    @ColumnInfo(name = "channel_name") val channelName: String,

    @ColumnInfo(name = "filters") val filters: List<AppletFilterType> = listOf(),

    @ColumnInfo(name = "isEnabled") val isEnabled :Boolean
)


object Converters {
    @TypeConverter
    fun fromString(value: String): List<AppletFilterType> {
        val listType = object: TypeToken<ArrayList<AppletFilterType>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list:List<AppletFilterType>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}