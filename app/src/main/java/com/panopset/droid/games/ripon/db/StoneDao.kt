package com.panopset.droid.games.ripon.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface StoneDao {

    @Query("SELECT * from stone_table ORDER BY name ASC")
    fun getStones(): LiveData<List<Stone>>

    @Query("SELECT * from stone_table where name = :stoneKey")
    suspend fun get(stoneKey: String): List<Stone>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stone: Stone)

    @Delete
    suspend fun delete(stone: Stone)

    @Query("DELETE FROM stone_table")
    suspend fun deleteAll()

    @Update(onConflict = REPLACE)
    suspend fun update(stone: Stone)

}
