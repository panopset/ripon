package com.panopset.droid.games.ripon

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.panopset.droid.games.ripples.FunView

class FunDrawScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fundraw_screen)
        val funView = findViewById<FunView>(R.id.funview)
        funView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN + View.SYSTEM_UI_FLAG_IMMERSIVE + View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
}
