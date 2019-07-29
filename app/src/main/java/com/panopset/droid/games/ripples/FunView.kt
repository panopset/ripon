package com.panopset.droid.games.ripples

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.panopset.droid.games.ripon.central.FastPainter
import com.panopset.droid.games.ripon.central.FastPainterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FunView(context: Context, attrs: AttributeSet) : SurfaceView(context, attrs),
    SurfaceHolder.Callback {
    private var running = true

    private var tag = "FunView SurfaceView"
    private lateinit var canvasBM: Canvas
    private lateinit var bitmap: Bitmap
    private lateinit var identityMatrix: Matrix

    init {
        setOnTouchListener { _, event ->
            fp.touched(event)
            true
        }
        holder.addCallback(this)
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, identityMatrix, null)
        invalidate()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        fp = FastPainterFactory.create()
        running = true
        val size = Point(width, height)
        display.getRealSize(size)
        bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888)
        identityMatrix = Matrix()
        canvasBM = Canvas()
        canvasBM.setBitmap(bitmap)
        CoroutineScope(Dispatchers.Default).launch {
            while (running) {
                fp.draw(canvasBM, left, top, width, height)
            }
        }
    }

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int, width: Int,
        height: Int
    ) {
        Log.i(tag, "surfaceChanged")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.i(tag, "surfaceDestroyed")
        running = false
    }

    var fp: FastPainter = FastPainterFactory.create()
}
