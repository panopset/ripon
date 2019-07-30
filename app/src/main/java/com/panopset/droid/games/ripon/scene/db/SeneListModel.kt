package com.panopset.droid.games.ripon.scene.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class SeneListModel(application: Application) : AndroidViewModel(application) {
    private val repository: SeneRepository
    private val allScenes: LiveData<List<Sene>>

    init {
        val seneDao = SeneRoomDatabase.getDatabase(application).seneDao()
        repository = SeneRepository(seneDao)
        allScenes = repository.allSenes
    }
}
