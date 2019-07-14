package com.panopset.droid.games.ripon.db

import androidx.lifecycle.LiveData

class StoneRepository(private val stoneDao: StoneDao) {
    val allStones: LiveData<List<Stone>> = stoneDao.getStones()

    suspend fun get(stoneKey: String): Stone? {
        val list = stoneDao.get(stoneKey)
        if (list.isNotEmpty()) {
            return list[0]
        }
        return null
    }

    suspend fun insert(stone: Stone) {
        stoneDao.insert(stone)
    }

    suspend fun delete(stone: Stone) {
        stoneDao.delete(stone)
    }

    suspend fun clear() {
        stoneDao.deleteAll()
    }

    suspend fun update(stone: Stone) {
        stoneDao.update(stone)
    }
}
