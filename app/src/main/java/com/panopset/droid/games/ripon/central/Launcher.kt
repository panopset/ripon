package com.panopset.droid.games.ripon.central

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import com.panopset.droid.games.ripon.FunDrawScreenActivity
import com.panopset.droid.games.ripon.R

class Launcher {
    fun go(act: Activity) {
        if (FastPainterFactory.isReady()) {
            act.startActivity(Intent(act, FunDrawScreenActivity::class.java))
        } else {
            Handler(act.mainLooper).post {
                Toast.makeText(
                    act.applicationContext,
                    R.string.empty_warning,
                    Toast.LENGTH_SHORT
                ).show()
            }// so 20059188
        }
    }
}
