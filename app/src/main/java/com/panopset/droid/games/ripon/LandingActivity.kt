package com.panopset.droid.games.ripon

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.panopset.droid.games.ripon.central.Launcher

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        findViewById<Button>(R.id.btn_config).setOnClickListener{
            startActivity(Intent(this@LandingActivity, EditScenesActivity::class.java))
        }
        findViewById<Button>(R.id.btn_go).setOnClickListener{ Launcher().go(this) }
    }
}
