package com.sms.pipe.data.db.dao

import androidx.room.*
import com.sms.pipe.data.db.entity.AppletEntity


@Dao
interface AppletDao {

    @Query("SELECT * FROM AppletEntity WHERE id = :id LIMIT 1")
    suspend fun getApplet(id:Long): AppletEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(applet: AppletEntity):Long

    @Query("SELECT * FROM AppletEntity")
    suspend fun getAllApplet():List<AppletEntity>

}