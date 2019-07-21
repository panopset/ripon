package com.panopset.droid.games.ripon

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Landing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        //findViewById<Button>(R.id.btn_go).setOnClickListener{ go() }
    }
}
