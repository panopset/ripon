package com.panopset.droid.games.ripples.skip

class Varial(var min: Int, var max: Int, private var vect: Float) {
    var value: Float = 0F

    operator fun next(): Float {
        value += vect
        if (value >= max) {
            vect = -1F
        } else if (value <= min) {
            vect = 1F
        }
        return value
    }

    fun reset() {
        value = min.toFloat()
    }
}
