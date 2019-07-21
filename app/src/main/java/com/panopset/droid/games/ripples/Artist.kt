package com.panopset.droid.games.ripples

import android.graphics.Canvas
import android.graphics.Rect
import android.view.MotionEvent
import com.panopset.droid.games.ripon.MainActivity
import com.panopset.droid.games.ripon.central.FastPainter
import com.panopset.droid.games.ripples.skip.BagOfStones

class Artist : FastPainter {
    private val backgroundPainter = BackgroundPainter()
    private val rect = Rect()
    private var isPaused = false

    init {
        backgroundPainter.clear()
        BagOfStones.init()
    }

    override fun draw(canvas: Canvas, x: Int, y: Int, w: Int, h: Int) {
        if (isPaused) {
            return
        }
        rect.set(x, y, w, h)
        backgroundPainter.paint(canvas, rect)
        for (stone in BagOfStones.stones.values) {
            if (!stone.started) {
                continue
            }
            Ripon.draw(canvas, rect, stone)
        }
    }

    override fun touched(event: MotionEvent) {
        val pointerIndex = event.actionIndex
        if (pointerIndex > 1) {
            togglePaused()
        } else {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> touchDown(event.x, event.y)
                MotionEvent.ACTION_UP -> touchUp(event.x, event.y)
            }
        }
    }

    override fun isReady(): Boolean {
        return BagOfStones.stones.isNotEmpty()
    }

    private var xDown = 0.0F
    private var yDown = 0.0F

    fun touchDown(x: Float, y: Float) {
        xDown = x
        yDown = y
    }

    fun touchUp(x: Float, y: Float) {
        if (BagOfStones.allClickStarted()) {
            BagOfStones.stopAllClickStarts()
        } else {
            for (skipper in BagOfStones.stones.values) {
                if (skipper.clickStart) {
                    if (!skipper.started) {
                        skipper.started = true
                        skipper.click(xDown, yDown, x, y)
                        break
                    }
                }
            }
        }
    }

    fun togglePaused() {
        isPaused = !isPaused
    }
}
