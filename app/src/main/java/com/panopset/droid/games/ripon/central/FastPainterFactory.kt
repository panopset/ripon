package com.panopset.droid.games.ripon.central

import com.panopset.droid.games.ripples.Artist
import com.panopset.droid.games.ripples.skip.BagOfStones

class FastPainterFactory {
    companion object {
        var nzr = false
        fun create(): FastPainter {
            if (nzr) {
                return Artist()
            } else {
                return Artist()
            }
        }
        fun setPreview(value: Boolean) {
            nzr = value
        }
        fun isReady(): Boolean {
            if (nzr) {
                return BagOfStones.stones.isNotEmpty()
            } else {
                return BagOfStones.stones.isNotEmpty()
            }
        }
    }
}
