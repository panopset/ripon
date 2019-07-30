package com.panopset.droid.games.ripon.scene.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Sene::class], version = 1)
abstract class SeneRoomDatabase : RoomDatabase() {

    abstract fun seneDao(): SeneDao

    companion object {
        @Volatile
        private var INSTANCE: SeneRoomDatabase? = null

        @Synchronized fun getDatabase(
            context: Context
        ): SeneRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SeneRoomDatabase::class.java,
                    "sene_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
