package com.panopset.droid.games.ripon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.panopset.droid.games.ripon.scene.SeneListAdapter
import com.panopset.droid.games.ripon.scene.db.SeneListModel
import kotlinx.android.synthetic.main.edit_scenes_list.*

class EditScenesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_scenes)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val adapter = SeneListAdapter(this)
        scenes_list.adapter = adapter
        scenes_list.layoutManager = LinearLayoutManager(this)
        seneListModel = ViewModelProviders.of(this).get(SeneListModel::class.java)
    }

    companion object {
        lateinit var seneListModel: SeneListModel
    }
}
