package com.panopset.droid.games.ripon.db

import android.graphics.*
import androidx.annotation.NonNull
import androidx.room.*
import com.panopset.droid.games.ripples.skip.ShapeShifter
import com.panopset.droid.games.ripples.skip.Varial

@Entity(tableName = "stone_table")
class Stone(
    @PrimaryKey @field:NonNull @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "xb") var xb: Int,
    @ColumnInfo(name = "yb") var yb: Int,
    @ColumnInfo(name = "dm") var dm: Int,
    @ColumnInfo(name = "dc") var dc: Int,
    @Embedded(prefix = "xv") var xv: ChangeBoundsDef,
    @Embedded(prefix = "yv") var yv: ChangeBoundsDef,
    @Embedded(prefix = "rdp") var redChange: ChangeBoundsDef,
    @Embedded(prefix = "gnp") var greenChange: ChangeBoundsDef,
    @Embedded(prefix = "blp") var blueChange: ChangeBoundsDef,
    @ColumnInfo(name = "click_start") var clickStart: Boolean,
    @ColumnInfo(name = "si") var shapeShifterSelectedIndex: Int
) {
    @Ignore @Transient
    var globalRandomColorStartFlag: Boolean = false
    @Ignore @Transient
    var isSleeping = false
    @Ignore @Transient
    var cycleCount = 0
    @Ignore @Transient
    var delayR = 0
    @Ignore @Transient
    var delayG = 0
    @Ignore @Transient
    var delayB = 0
    @Ignore @Transient
    var cx = 0.0F
    @Ignore @Transient
    var cy = 0.0F
    @Ignore @Transient
    var mx = 0.0F
    @Ignore @Transient
    var my = 0.0F
    @Ignore @Transient
    var rd = Varial(0, 255, 1F)
    @Ignore @Transient
    var gn = Varial(0, 255, 1F)
    @Ignore @Transient
    var bl = Varial(0, 255, 1F)
    @Ignore @Transient
    val radiusx = Varial(0, 10000, 1F)
    @Ignore @Transient
    val radiusy = Varial(0, 10000, 1F)

    @Ignore @Transient
    var started: Boolean = false
    @Ignore @Transient
    val paint = Paint()
    @Ignore @Transient
    var shapeShifter = ShapeShifter.RECT

    init {
        paint.style = Paint.Style.STROKE
        reset()
    }

    private fun reset() {
        shapeShifter = ShapeShifter.values()[shapeShifterSelectedIndex]
        radiusx.max = xb
        radiusy.max = yb
        started = false
    }

    fun click(xDown: Float, yDown: Float, xUp: Float, yUp: Float) {
        this.mx = xUp - xDown
        this.my = yUp - yDown
        this.cx = xDown
        this.cy = yDown
        resetVarials()
    }

    private fun resetVarials() {
        rd = Varial(redChange.min, redChange.max, redChange.vector)
        gn = Varial(greenChange.min, greenChange.max, greenChange.vector)
        bl = Varial(blueChange.min, blueChange.max, blueChange.vector)
        if (redChange.max > 0) {
            if (redChange.randomStart) {
                rd.value = (rand(rd.max - rd.min) + rd.min).toFloat()
            } else {
                rd.value = redChange.start.toFloat()
            }
        }
        if (greenChange.max > 0) {
            if (greenChange.randomStart) {
                gn.value = (rand(gn.max - gn.min) + gn.min).toFloat()
            } else {
                gn.value = greenChange.start.toFloat()
            }
        }
        if (blueChange.max > 0) {
            if (blueChange.randomStart) {
                bl.value = (rand(bl.max - bl.min) + bl.min).toFloat()
            } else {
                bl.value = blueChange.start.toFloat()
            }
        }
        radiusx.max = xb
        radiusy.max = yb
        radiusx.reset()
        radiusy.reset()
    }

    private fun rand(max: Int): Int {
        return (Math.random() * max).toInt()
    }
}

@Entity(tableName = "change_bounds")
class ChangeBoundsDef(
    @ColumnInfo(name = "start") var start: Int,
    @ColumnInfo(name = "min") var min: Int,
    @ColumnInfo(name = "max") var max: Int,
    @ColumnInfo(name = "delay") var delay: Int,
    @ColumnInfo(name = "vector") var vector: Float,
    @ColumnInfo(name = "random_start") var randomStart: Boolean,
    @ColumnInfo(name = "bounce_flag") var bounceFlag: Boolean
)
