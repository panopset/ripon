package com.panopset.droid.games.ripon.scene.db

class SeneFactory {
    companion object {
        fun defaultSene(
            seneName: String
        ): Sene {
            val rtnSene = dupSene
            return if (rtnSene == null) {
                Sene(seneName)
            } else {
                dupSene = null
                rtnSene.name = seneName
                rtnSene
            }
        }
        var dupSene: Sene? = null
    }
}
