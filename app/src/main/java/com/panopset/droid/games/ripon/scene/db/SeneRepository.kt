package com.panopset.droid.games.ripon.scene.db

import androidx.lifecycle.LiveData

class SeneRepository(private val seneDao: SeneDao) {
    val allSenes: LiveData<List<Sene>> = seneDao.getSenes()

    suspend fun get(seneKey: String): Sene? {
        val list = seneDao.get(seneKey)
        if (list.isNotEmpty()) {
            return list[0]
        }
        return null
    }

    suspend fun insert(sene: Sene) {
        seneDao.insert(sene)
    }

    suspend fun delete(sene: Sene) {
        seneDao.delete(sene)
    }

    suspend fun clear() {
        seneDao.deleteAll()
    }

    suspend fun update(sene: Sene) {
        seneDao.update(sene)
    }
}
