package com.sms.pipe.data.db.dao

import androidx.room.*
import com.sms.pipe.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE logged = 1 LIMIT 1")
    fun getLoggedUser(): Flow<UserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("UPDATE UserEntity SET logged = 0 WHERE logged =1")
    fun logout():Int
}