package com.panopset.droid.games.ripon.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Stone::class], version = 18)
abstract class StoneRoomDatabase : RoomDatabase() {

    abstract fun stoneDao(): StoneDao

    companion object {
        @Volatile
        private var INSTANCE: StoneRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): StoneRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StoneRoomDatabase::class.java,
                    "stone_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        val DEFAULT_CHANGE_BOUNDS =
            ChangeBoundsDef(0, 0, 255, 0, 1F, randomStart = true, bounceFlag = true)
    }
}
