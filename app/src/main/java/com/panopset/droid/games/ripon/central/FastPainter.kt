package com.panopset.droid.games.ripon.central

import android.graphics.Canvas
import android.view.MotionEvent

interface FastPainter {

    fun draw(canvas: Canvas, x: Int, y: Int, w: Int, h: Int)

    fun touched(event: MotionEvent)

    fun isReady(): Boolean

    fun pause()
}
