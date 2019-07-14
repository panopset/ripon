package com.panopset.droid.games.ripples

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect

class BackgroundPainter {

    init {
        getPaint()
    }

    fun paint(canvas: Canvas, rect: Rect) {
        if (dirty) {
            canvas.drawRect(rect, getPaint())
            dirty = false
        }
    }

    private var paint: Paint? = null
    private fun getPaint(): Paint {
        if (paint == null) {
            paint = Paint()
            paint!!.color = Color.BLACK
        }
        return paint as Paint
    }

    fun clear() {
        dirty = true
    }

    private var dirty = true

}
