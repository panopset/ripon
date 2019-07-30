package com.panopset.droid.games.ripon.scene.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SeneDao {
    @Query("SELECT * from sene_table ORDER BY name ASC")
    fun getSenes(): LiveData<List<Sene>>

    @Query("SELECT * from sene_table where name = :seneKey")
    suspend fun get(seneKey: String): List<Sene>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(sene: Sene)

    @Delete
    suspend fun delete(sene: Sene)

    @Query("DELETE FROM sene_table")
    suspend fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(sene: Sene)
}
