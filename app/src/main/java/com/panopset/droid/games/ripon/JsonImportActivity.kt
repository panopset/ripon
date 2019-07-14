package com.panopset.droid.games.ripon

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.panopset.droid.games.ripon.db.Stone

class JsonImportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.json_import)
        val textToImport = findViewById<TextView>(R.id.textToImport)
        val importBtn = findViewById<Button>(R.id.btn_import)
        importBtn.setOnClickListener {
            try {
                val stones: Array<Stone> =
                    Gson().fromJson(textToImport.text.toString(), Array<Stone>::class.java)
                for (stone in stones) {
                    MainActivity.stoneViewModel.upsert(stone)
                }
                Intent(this@JsonImportActivity, MainActivity::class.java).apply {
                    startActivity(this)
                }
            } catch(ex: Exception) {
                Toast.makeText(
                    applicationContext,
                    R.string.import_warning,
                    Toast.LENGTH_LONG
                ).show()
                Log.w(this.javaClass.name, ex.message, ex)
            }
        }
    }
}
