package com.panopset.droid.games.ripon.scene.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Scene::class], version = 1)
abstract class ScenesRoomDatabase : RoomDatabase() {

    abstract fun scenesDao(): ScenesDao

    companion object {
        @Volatile
        private var INSTANCE: ScenesRoomDatabase? = null

        @Synchronized fun getDatabase(
            context: Context
        ): ScenesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScenesRoomDatabase::class.java,
                    "scenes_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
