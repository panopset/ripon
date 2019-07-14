package com.panopset.droid.games.ripon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.panopset.droid.games.ripon.db.Stone

class StoneListAdapter internal constructor(
    parentActivity: MainActivity
) : RecyclerView.Adapter<StoneListAdapter.StoneViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(parentActivity)
    private var stones = emptyList<Stone>() // Cached copy of stones.

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        val stone = it.tag as Stone
        EditStoneIntentFactory(parentActivity, stone.name).editStone()
    }

    inner class StoneViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stoneItemView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoneViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return StoneViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StoneViewHolder, position: Int) {
        val current: Stone = stones[position]
        holder.stoneItemView.text = current.name
        with(holder.stoneItemView) {
            tag = current
            setOnClickListener(onClickListener)
        }
    }

    internal fun setStones(stones: List<Stone>) {
        this.stones = stones
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return stones.size
    }
}
