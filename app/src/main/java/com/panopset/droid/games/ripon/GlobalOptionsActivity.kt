package com.panopset.droid.games.ripon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.panopset.droid.games.ripon.db.Stone
import com.panopset.droid.textslider.TextSlider
import kotlinx.coroutines.runBlocking

class GlobalOptionsActivity : AppCompatActivity() {

    private lateinit var throttleMillis: TextSlider
    private lateinit var throttleCycle: TextSlider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.global_options_activity)
        throttleMillis = findViewById(R.id.globalThrottleMillis)
        throttleCycle = findViewById(R.id.globalThrottleCycles)
        findViewById<Button>(R.id.btnApply).setOnClickListener{ doApply() }
        findViewById<Button>(R.id.btnCancelGlobalUpdate).setOnClickListener{ doCancel() }
    }

    private fun doCancel() {
        startActivity(Intent(this@GlobalOptionsActivity, MainActivity::class.java))
    }

    private fun doApply() {
        runBlocking {
            if (MainActivity.stoneViewModel.allStones.value != null) {
                val stones: List<Stone>? = MainActivity.stoneViewModel.allStones.value
                if (stones != null) {
                    for (stone in stones) {
                        stone.dm = throttleMillis.getValue()
                        stone.dc = throttleCycle.getValue()
                        MainActivity.stoneViewModel.upsert(stone)
                    }
                }
            }
            startActivity(Intent(this@GlobalOptionsActivity, MainActivity::class.java))
        }
    }
}
