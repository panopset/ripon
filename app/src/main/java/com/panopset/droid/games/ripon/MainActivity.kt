package com.panopset.droid.games.ripon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.panopset.droid.games.ripon.central.Launcher
import com.panopset.droid.games.ripon.db.Stone
import com.panopset.droid.games.ripon.db.StoneFactory
import com.panopset.droid.games.ripon.db.StoneViewModel
import kotlinx.android.synthetic.main.include_stone_list_scroller.*
import kotlinx.android.synthetic.main.stone_list.*
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private val newStoneActivityRequestCode = 1

    companion object {
        lateinit var stoneViewModel: StoneViewModel
        var singlePane: Boolean = false
        const val DUPLICATE_STONE_KEY = "duplicate"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_YES
        )
        setContentView(R.layout.activity_main)
        singlePane = stone_detail_container == null
        this.title = resources.getString(R.string.title_stone_list)//so 3488664
        setupRecyclerView()
        setupControlPanel()
        val duplicateKey = intent.getStringExtra(DUPLICATE_STONE_KEY)
        if (duplicateKey != null) {
            runBlocking {
                val stoneToDuplicate = stoneViewModel.get(duplicateKey)
                if (stoneToDuplicate != null) {
                    StoneFactory.dupStone =
                        Gson().fromJson(Gson().toJson(stoneToDuplicate), Stone::class.java)
                    add()
                }
            }
        }
    }

    private fun setupControlPanel() {
        findViewById<Button>(R.id.cmd_go).setOnClickListener { Launcher().go(this) }
        findViewById<Button>(R.id.cmd_clear).setOnClickListener { clear() }
        findViewById<Button>(R.id.cmd_add).setOnClickListener { add() }
        findViewById<Button>(R.id.cmd_import).setOnClickListener { import() }
        findViewById<Button>(R.id.cmd_share).setOnClickListener { share() }
        findViewById<Button>(R.id.cmd_options).setOnClickListener { options() }
        findViewById<Button>(R.id.cmd_preview).setOnClickListener { preview() }
    }

    private fun clear() {
        stoneViewModel.clear()
    }

    private fun add() {
        val addStoneIntent = Intent(this@MainActivity, NewStoneActivity::class.java)
        startActivityForResult(addStoneIntent, newStoneActivityRequestCode)
    }

    private fun import() {
        startActivity(Intent(this@MainActivity, JsonImportActivity::class.java))
    }

    private fun share() {
        val stones = stoneViewModel.allStones.value
        if (stones == null || stones.isEmpty()) {
            Toast.makeText(applicationContext, R.string.empty_not_shared, Toast.LENGTH_LONG).show()
            return
        }
        val json = Gson().toJson(stones)
        Intent().apply {
            putExtra(Intent.EXTRA_TEXT, json)
            type = "text/plain"
            action = Intent.ACTION_SEND
            startActivity(this)
        }
    }

    private fun options() {
        startActivity(Intent(this@MainActivity, GlobalOptionsActivity::class.java))
    }

    private fun preview() {
        startActivity(Intent(this@MainActivity, Landing::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (requestCode == newStoneActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val stone =
                    StoneFactory.defaultStone(data.getStringExtra(NewStoneActivity.EXTRA_REPLY))
                stoneViewModel.insert(stone)
                EditStoneIntentFactory(this, stone.name).editStone()
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
        val adapter = StoneListAdapter(this)
        stone_list.adapter = adapter
        stone_list.layoutManager = LinearLayoutManager(this)
        stoneViewModel = ViewModelProviders.of(this).get(StoneViewModel::class.java)
        stoneViewModel.allStones.observe(this, Observer { stones ->
            stones?.let { adapter.setStones(it) }
        })
    }
}
