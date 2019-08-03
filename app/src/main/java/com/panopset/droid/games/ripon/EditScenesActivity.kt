package com.panopset.droid.games.ripon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.panopset.droid.games.ripon.scene.SeneListAdapter
import com.panopset.droid.games.ripon.scene.db.Sene
import com.panopset.droid.games.ripon.scene.db.SeneFactory
import com.panopset.droid.games.ripon.scene.db.SeneViewModel
import kotlinx.android.synthetic.main.activity_new_sene.*
import kotlinx.android.synthetic.main.edit_scenes_list.*
import kotlinx.coroutines.runBlocking

class EditScenesActivity : AppCompatActivity() {

    private val newSeneActivityRequestCode = 2

    companion object {
        lateinit var seneViewModel: SeneViewModel
        const val DUPLICATE_KEY = "duplicate"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_scenes)
        setupRecyclerView()
        setupDuplicateKey()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (requestCode == newSeneActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let {data ->
                val sene =
                    SeneFactory.defaultSene(data.getStringExtra(NewSeneActivity.EXTRA_REPLY))
                seneViewModel.insert(sene)

                val intent = Intent(this, EditSeneActivity::class.java)
                intent.putExtra(EditSeneActivity.SENE_KEY, newSeneName.text)
                startActivity(intent)

//              EditSeneIntentFactory(this, sene.name).editSene()
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setupRecyclerView() {
        val adapter = SeneListAdapter(this)
        scenes_list.adapter = adapter
        scenes_list.layoutManager = LinearLayoutManager(this)
        seneViewModel = ViewModelProviders.of(this).get(SeneViewModel::class.java)
    }

    private fun setupDuplicateKey() {
        val duplicateKey = intent.getStringExtra(DUPLICATE_KEY)
        if (duplicateKey != null) {
            runBlocking {
                val seneToDuplicate = seneViewModel.get(duplicateKey)
                if (seneToDuplicate != null) {
                    SeneFactory.dupSene =
                        Gson().fromJson(Gson().toJson(seneToDuplicate), Sene::class.java)
                }
            }
        }
    }
}
