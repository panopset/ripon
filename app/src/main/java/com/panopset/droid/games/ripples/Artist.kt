package com.panopset.droid.games.ripples

import android.graphics.Canvas
import android.graphics.Rect
import com.panopset.droid.games.ripples.skip.BagOfStones

class Artist {
    private val backgroundPainter = BackgroundPainter()
    private val rect = Rect()
    private var isPaused = false

    init {
        backgroundPainter.clear()
    }

    fun draw(canvas: Canvas, x: Int, y: Int, w: Int, h: Int) {
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
