package com.panopset.droid.games.ripon.scene.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.transition.Scene
import com.panopset.droid.games.ripon.db.StoneRoomDatabase

class ScenesListModel(application: Application) : AndroidViewModel(application) {
    private val repository: ScenesRepository
    private val allScenes: LiveData<List<Scene>>

    init {
        val scenesDao = ScenesRoomDatabase.getDatabase(application).sceneDao
    }
}
