package com.panopset.droid.games.ripon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class NewSeneActivity : AppCompatActivity() {
    private lateinit var newSeneNameEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sene)
        newSeneNameEditText = findViewById(R.id.newSeneName)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(newSeneNameEditText.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val name = newSeneNameEditText.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, name)
                setResult(Activity.RESULT_OK, replyIntent)
            }
        }
    }


    companion object {
        const val EXTRA_REPLY = "com.panopset.droid.REPLY"
    }
}
