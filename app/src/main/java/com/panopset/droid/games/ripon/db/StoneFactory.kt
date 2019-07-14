package com.panopset.droid.games.ripon.db

class StoneFactory {
    companion object {
        fun defaultStone(
            stoneName: String
        ): Stone {
            val rtnStone = dupStone
            return if (rtnStone == null) {
                Stone(
                    stoneName,
                    1000,
                    1000,
                    0,
                    0,
                    StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS,
                    StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS,
                    StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS,
                    StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS,
                    StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS,
                    true,
                    0
                )
            } else {
                dupStone = null
                rtnStone.name = stoneName
                rtnStone
            }
        }
        var dupStone: Stone? = null
    }
}
