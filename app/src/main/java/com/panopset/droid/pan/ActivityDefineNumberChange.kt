package com.panopset.droid.pan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.panopset.droid.games.ripon.R
import com.panopset.droid.games.ripon.db.ChangeBoundsDef
import com.panopset.droid.games.ripon.db.StoneRoomDatabase
import com.panopset.droid.textslider.TextSlider

class ActivityDefineNumberChange : AppCompatActivity() {
    companion object {
        var changeBounds: ChangeBoundsDef? = null
        const val JSON_KEY = "JSON"
    }

    private lateinit var nsStart: TextSlider
    private lateinit var nsMin: TextSlider
    private lateinit var nsMax: TextSlider
    private lateinit var nsDelay: TextSlider
    private lateinit var nsBounceFlag: CheckBox
    private lateinit var nsRandomStartFlag: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_define_number_change)
        nsStart = findViewById(R.id.nsStart)
        nsMin = findViewById(R.id.nsMin)
        nsMax = findViewById(R.id.nsMax)
        nsDelay = findViewById(R.id.nsDelay)
        nsBounceFlag = findViewById(R.id.nsBounce)
        nsRandomStartFlag = findViewById(R.id.nsRandom)
        if (changeBounds == null) {
            changeBounds = StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS
            Log.e(this.javaClass.name, "changeBounds not set!")
            return
        }
        nsStart.setValue(changeBounds!!.start)
        nsMin.setValue(changeBounds!!.min)
        nsMax.setValue(changeBounds!!.max)
        nsDelay.setValue(changeBounds!!.delay)
        nsBounceFlag.isChecked = changeBounds!!.bounceFlag
        nsRandomStartFlag.isChecked = changeBounds!!.randomStart
        nsMin.setMaximum(changeBounds!!.min)
        nsMax.setMaximum(changeBounds!!.max)
        nsStart.setMaximum(changeBounds!!.start)

        val saveButton = findViewById<Button>(R.id.nsSave)
        saveButton.setOnClickListener {
            changeBounds?.start = getStart()
            changeBounds?.min = getMin()
            changeBounds?.max = getMax()
            changeBounds?.delay = getDelay()
            changeBounds?.bounceFlag = getBounce()
            changeBounds?.randomStart = getRandomStartFlag()
            val intent = Intent()
            intent.putExtra(JSON_KEY, Gson().toJson(changeBounds))
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun getStart(): Int {
        return nsStart.getValue()
    }

    private fun getMin(): Int {
        return nsMin.getValue()
    }

    private fun getMax(): Int {
        return nsMax.getValue()
    }

    private fun getDelay(): Int {
        return nsDelay.getValue()
    }

    private fun getBounce(): Boolean {
        return nsBounceFlag.isChecked
    }

    private fun getRandomStartFlag(): Boolean {
        return nsRandomStartFlag.isChecked
    }
}
