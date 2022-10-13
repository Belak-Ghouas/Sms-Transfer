package com.sms.pipe.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sms.pipe.data.db.dao.AppletDao
import com.sms.pipe.data.db.dao.UserDao
import com.sms.pipe.data.db.entity.AppletEntity
import com.sms.pipe.data.db.entity.Converters
import com.sms.pipe.data.db.entity.UserEntity

@Database(entities = [UserEntity::class,AppletEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun appletDao():AppletDao

    companion object {

        private const val DATABASE_NAME = "sms_pipe_database"

        @Volatile private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME).build()
        }

    }
}