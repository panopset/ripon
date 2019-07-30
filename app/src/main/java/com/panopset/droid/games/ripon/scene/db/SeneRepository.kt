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
}
