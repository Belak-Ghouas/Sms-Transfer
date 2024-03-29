package com.sms.pipe.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sms.pipe.data.db.entity.AppletEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface AppletDao {

    @Query("SELECT * FROM AppletEntity WHERE applet_id = :id LIMIT 1")
    suspend fun getApplet(id:Long): AppletEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(applet: AppletEntity):Long

    @Query("SELECT * FROM AppletEntity WHERE user_id = :username ")
    fun getAllApplet(username:String):Flow<List<AppletEntity>>

    @Query("SELECT * FROM AppletEntity WHERE isEnabled = 1 AND user_id = :username")
    suspend fun getEnabledApplets(username:String):List<AppletEntity>

    @Query("DELETE FROM AppletEntity WHERE applet_id = :id")
    suspend fun deleteById(id: Long): Int
}