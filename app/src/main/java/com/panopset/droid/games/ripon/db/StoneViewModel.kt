package com.panopset.droid.games.ripon.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StoneViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StoneRepository

    // TODO: Make this private.
    val allStones: LiveData<List<Stone>>

    init {
        val stonesDao = StoneRoomDatabase.getDatabase(application).stoneDao()
        repository = StoneRepository(stonesDao)
        allStones = repository.allStones
    }

    suspend fun get(stoneKey: String): Stone? {
        return repository.get(stoneKey)
    }

    fun insert(stone: Stone) = viewModelScope.launch {
        repository.insert(stone)
    }

    fun delete(stone: Stone) = viewModelScope.launch {
        repository.delete(stone)
    }

    fun clear() = viewModelScope.launch {
        repository.clear()
    }

    fun upsert(stone: Stone) = viewModelScope.launch {
        insert(stone)
        repository.update(stone)
    }
}
