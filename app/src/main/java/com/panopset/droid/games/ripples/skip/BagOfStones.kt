package com.panopset.droid.games.ripples.skip

import com.google.gson.Gson
import com.panopset.droid.games.ripon.MainActivity
import com.panopset.droid.games.ripon.db.Stone
import java.util.*

object BagOfStones {

    val stones: MutableMap<String, Stone> = Collections.synchronizedSortedMap(TreeMap())

    private fun add(stone: Stone) {
        this.stones[stone.name] = stone
    }

    fun import(json: String) {
        if (json.isEmpty()) {
            return
        }
        stones.clear()
        for (stone in Gson().fromJson(json, Array<Stone>::class.java)) {
            add(stone)
        }
    }

    fun stopAllClickStarts() {
        for (stone in stones.values) {
            if (stone.clickStart) {
                stone.started = false
            }
        }
    }

    fun allClickStarted(): Boolean {
        for (skipper in stones.values) {
            if (skipper.clickStart) {
                if (!skipper.started) {
                    return false
                }
            }
        }
        return true
    }

    fun init() {
        val stones: List<Stone>? = MainActivity.stoneViewModel.allStones.value
        this.stones.clear()
        if (stones != null && stones.isNotEmpty()) {
            for (stone in stones) {
                this.stones[stone.name] = stone
            }
        }
    }
}
