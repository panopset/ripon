package com.panopset.droid.games.ripples

import android.graphics.*
import com.panopset.droid.games.ripon.db.Stone
import com.panopset.droid.games.ripples.skip.ShapeShifter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Ripon {
    companion object {
        fun draw(canvas: Canvas, screenRect: Rect, stone: Stone) {
            with(stone) {
                if (isSleeping) {
                    return
                }
                if (dc > 0) {
                    if (cycleCount++ > dc) {
                        cycleCount = 0
                    } else {
                        return
                    }
                }
                val centerScreenX = screenRect.width() / 2F
                val centerScreenY = screenRect.height() / 2F


                if (++delayR >= redChange.delay && rd.max > 0) {
                    rd.next()
                    delayR = 0
                }
                if (++delayG >= greenChange.delay && gn.max > 0) {
                    gn.next()
                    delayG = 0
                }
                if (++delayB >= blueChange.delay && bl.max > 0) {
                    bl.next()
                    delayB = 0
                }
                paint.color = Color.rgb(rd.value.toInt(), gn.value.toInt(), bl.value.toInt())
                val rxn = radiusx.next()
                val ryn = radiusy.next()
                val boundsRect = RectF(cx - rxn, cy - rxn, cx + ryn, cy + ryn)
                when (shapeShifter) {
                    ShapeShifter.RECT -> {
                        canvas.drawRect(boundsRect, paint)
                    }
                    ShapeShifter.R_LINE -> {
                        canvas.drawLine(boundsRect.left+mx, boundsRect.top, boundsRect.right, boundsRect.bottom, paint)
                    }
                    ShapeShifter.L_LINE -> {
                        canvas.drawLine(boundsRect.left, boundsRect.bottom, boundsRect.right, boundsRect.top, paint)
                    }
                    ShapeShifter.X -> {
                        canvas.drawLine(boundsRect.left, boundsRect.top, boundsRect.right, boundsRect.bottom, paint)
                        canvas.drawLine(boundsRect.left, boundsRect.bottom, boundsRect.right, boundsRect.top, paint)
                    }
                    ShapeShifter.OVAL -> {
                        if (radiusx.value == radiusy.value) {
                            canvas.drawCircle(cx, cy, rxn, paint)
                        } else {
                            canvas.drawOval(boundsRect, paint)
                        }
                    }
                    ShapeShifter.NAME -> {
                        val textWidth = paint.measureText(name)
                        setTextSizeForWidth(
                            paint,
                            boundsRect.right - boundsRect.left,
                            name
                        )
                        canvas.drawText(name, cx - (textWidth / 2), cy, paint)
                    }
                }
                cx += mx
                cy += my
                if (xv.bounceFlag) {
                    if ((cx > centerScreenX + xb && mx > 0) || (cx < centerScreenX - xb && mx < 0)) {
                        mx *= -1F
                    }
                }
                if (yv.bounceFlag) {
                    if ((cy > centerScreenY + yb && my > 0) || (cy < centerScreenY - yb && my < 0)) {
                        my *= -1F
                    }
                }
                if (dm > 0) {
                    isSleeping = true
                    CoroutineScope(Dispatchers.Default).launch {
                        Thread.sleep(dm.toLong())
                        isSleeping = false
                    }
                }
            }
        }

        /**
         * https://stackoverflow.com/questions/12166476
         * Sets the text size for a Paint object so a given string of text will be a
         * given width.
         *
         * @param paint
         * the Paint to set the text size for
         * @param desiredWidth
         * the desired width
         * @param text
         * the text that should be that width
         */
        private fun setTextSizeForWidth(
            paint: Paint, desiredWidth: Float,
            text: String
        ) {

            // Pick a reasonably large value for the test. Larger values produce
            // more accurate results, but may cause problems with hardware
            // acceleration. But there are workarounds for that, too; refer to
            // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
            val testTextSize = 48f

            // Get the bounds of the text, using our testTextSize.
            paint.textSize = testTextSize
            val bounds = Rect()
            paint.getTextBounds(text, 0, text.length, bounds)

            // Calculate the desired size as a proportion of our testTextSize.
            val desiredTextSize = testTextSize * desiredWidth / bounds.width()

            // Set the paint for that size.
            paint.textSize = desiredTextSize
        }
    }
}