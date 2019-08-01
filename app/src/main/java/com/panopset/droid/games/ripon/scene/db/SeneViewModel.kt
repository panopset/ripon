package com.panopset.droid.games.ripon.scene.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SeneViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SeneRepository
    private val allScenes: LiveData<List<Sene>>

    init {
        val seneDao = SeneRoomDatabase.getDatabase(application).seneDao()
        repository = SeneRepository(seneDao)
        allScenes = repository.allSenes
    }

    suspend fun get(seneKey: String): Sene? {
        return repository.get(seneKey)
    }

    fun insert(sene: Sene) = viewModelScope.launch {
        repository.delete(sene)
    }

    fun delete(sene: Sene) = viewModelScope.launch {
        repository.delete(sene)
    }

    fun clear() = viewModelScope.launch {
        repository.clear()
    }

    fun upsert(sene: Sene) = viewModelScope.launch {
        insert(sene)
        repository.update(sene)
    }
}
