package com.panopset.droid.games.ripon.scene

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.panopset.droid.games.ripon.EditScenesActivity
import com.panopset.droid.games.ripon.R

class SceneListAdapter internal constructor(
    parentActivity: EditScenesActivity
) : RecyclerView.Adapter<SceneListAdapter.SceneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: SceneViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class SceneViewHolder constructor(sceneView: View) : RecyclerView.ViewHolder(sceneView) {
        val sceneItemView: TextView = sceneView.findViewById(R.id.scene_list_summary)
    }
}
