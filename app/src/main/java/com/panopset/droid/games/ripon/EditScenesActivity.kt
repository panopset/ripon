package com.panopset.droid.games.ripon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.panopset.droid.games.ripon.scene.SceneListAdapter
import kotlinx.android.synthetic.main.edit_scenes_list.*

class EditScenesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_scenes)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = SceneListAdapter(this)
        scenes_list.adapter = adapter
        scenes_list.layoutManager = LinearLayoutManager(this)
        scenesListModel = ViewModelProviders.of(this).get(ScenesListModel::class.java)
    }
}
