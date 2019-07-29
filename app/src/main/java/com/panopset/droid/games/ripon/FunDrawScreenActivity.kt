package com.panopset.droid.games.ripon

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.panopset.droid.games.ripples.FunView

class FunDrawScreenActivity : Activity() {
    lateinit var funView: FunView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fundraw_screen)
        funView = findViewById(R.id.funview)
        funView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_FULLSCREEN + View.SYSTEM_UI_FLAG_IMMERSIVE + View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (funView != null) {
                funView.fp.pause()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
