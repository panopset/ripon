package com.panopset.droid.games.ripon

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditSeneActivity : AppCompatActivity() {

    private lateinit var sene_name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_sene_activity)
        sene_name = findViewById(R.id.sene_name)
    }

    companion object {
        const val SENE_KEY = "seneKey"
    }
}