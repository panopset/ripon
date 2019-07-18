package com.panopset.droid.games.ripon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.panopset.droid.games.ripon.db.ChangeBoundsDef
import com.panopset.droid.games.ripon.db.Stone
import com.panopset.droid.games.ripples.skip.ShapeShifter
import com.panopset.droid.pan.ActivityDefineNumberChange
import com.panopset.droid.pan.NumberChange
import com.panopset.droid.textslider.TextSlider
import kotlinx.android.synthetic.main.edit_stone_fragment.*
import kotlinx.coroutines.runBlocking

class EditStoneFragment : Fragment() {

    private lateinit var rootView: View

    private lateinit var redNumberChange: NumberChange
    private lateinit var greenNumberChange: NumberChange
    private lateinit var blueNumberChange: NumberChange

    private var mStone: Stone? = null

    private lateinit var scName: TextView
    private lateinit var throttleMillis: TextSlider
    private lateinit var throttleCycles: TextSlider
    private lateinit var scXbounds: TextSlider
    private lateinit var scYbounds: TextSlider
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.edit_stone_fragment, container, false)
        throttleMillis = rootView.findViewById(R.id.throttleMillis)
        throttleCycles = rootView.findViewById(R.id.throttleCycles)
        scXbounds = rootView.findViewById(R.id.xbounds)
        scYbounds = rootView.findViewById(R.id.ybounds)

        val stoneKey = if (MainActivity.singlePane) {
            activity?.intent?.extras?.get(STONE_KEY) as String
        } else {
            arguments?.getString(STONE_KEY)
        }
        runBlocking {
            if (stoneKey != null && !stoneKey.isBlank()) {
                var stone = MainActivity.stoneViewModel.get(stoneKey)
                if (stone == null) {
                    Toast.makeText(
                        context,
                        "loading...",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    scName = rootView.findViewById(R.id.sc_name)
                    val cancelBtn = rootView.findViewById<Button>(R.id.sc_cancel)
                    if (MainActivity.singlePane) {
                        cancelBtn.setOnClickListener {
                            if (MainActivity.singlePane) {
                                val intent = Intent(context, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    } else {
                        val layout = cancelBtn.parent as ViewGroup
                        layout.removeView(cancelBtn)
                    }
                    val saveBtn = rootView.findViewById<Button>(R.id.sc_save)
                    saveBtn.setOnClickListener {
                        runBlocking {
                            save(stone)
                            if (MainActivity.singlePane) {
                                val intent = Intent(context, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }
                    val duplicateBtn = rootView.findViewById<Button>(R.id.sc_duplicate)
                    duplicateBtn.setOnClickListener{
                        runBlocking {
                            save(stone)
                            val intent = Intent(context, MainActivity::class.java)
                            intent.putExtra(MainActivity.DUPLICATE_STONE_KEY, stone.name)
                            startActivity(intent)
                        }
                    }
                    val deleteBtn = rootView.findViewById<Button>(R.id.sc_delete)
                    deleteBtn.setOnClickListener {
                        runBlocking {
                            stone.let { it1 -> MainActivity.stoneViewModel.delete(it1) }
                        }
                        startActivity(Intent(context, MainActivity::class.java))
                    }
                    scName.text = stone.name
                    throttleMillis.setValue(stone.dm)
                    throttleCycles.setValue(stone.dc)
                    scXbounds.setValue(stone.xb)
                    scYbounds.setValue(stone.yb)

                    val shapeShifterSpinner = rootView.findViewById<Spinner>(R.id.spinner_shape_shifter)

                    shapeShifterSpinner.adapter = ArrayAdapter(
                        context!!,
                        android.R.layout.simple_spinner_dropdown_item,
                        ShapeShifter.values()
                    )

                    shapeShifterSpinner.setSelection(stone.shapeShifterSelectedIndex)
                    shapeShifterSpinner.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                stone.shapeShifterSelectedIndex = position
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                            }
                        }
                    mStone = stone
                    bindGlobalRandomColorStart(rootView)
                    bindColorChanges(rootView)
                }
            }
        }
        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }
        val numberChange = when (requestCode) {
            redNumberChange.uniqueID -> redNumberChange
            greenNumberChange.uniqueID -> greenNumberChange
            blueNumberChange.uniqueID -> blueNumberChange
            else -> redNumberChange
        }
        with(data.getStringExtra(ActivityDefineNumberChange.JSON_KEY)) {
            val changeBoundsDef = Gson().fromJson(this, ChangeBoundsDef::class.java)
            numberChange.setChangeBounds(changeBoundsDef)
        }
    }

    private fun save(stone: Stone) {
        stone.dm = throttleMillis.getValue()
        stone.dc = throttleCycles.getValue()
        stone.xb = scXbounds.getValue()
        stone.yb = scYbounds.getValue()
        stone.globalRandomColorStartFlag = global_random_color_start.isChecked
        stone.redChange.randomStart = redNumberChange.getChangeBounds().randomStart
        stone.greenChange.randomStart = greenNumberChange.getChangeBounds().randomStart
        stone.blueChange.randomStart = blueNumberChange.getChangeBounds().randomStart
        stone.shapeShifterSelectedIndex = spinner_shape_shifter.selectedItemPosition
        MainActivity.stoneViewModel.upsert(stone)
    }

    private fun bindColorChanges(rootView: View) {
        redNumberChange = rootView.findViewById(R.id.redNumberChange)
        greenNumberChange = rootView.findViewById(R.id.greenNumberChange)
        blueNumberChange = rootView.findViewById(R.id.blueNumberChange)


        redNumberChange.parentView = rootView
        redNumberChange.parentFragment = this
        redNumberChange.setChangeBounds(mStone?.redChange!!)

        greenNumberChange.parentView = rootView
        greenNumberChange.parentFragment = this
        greenNumberChange.setChangeBounds(mStone?.greenChange!!)

        blueNumberChange.parentView = rootView
        blueNumberChange.parentFragment = this
        blueNumberChange.setChangeBounds(mStone?.blueChange!!)
   }

    private fun bindGlobalRandomColorStart(rootView: View) {
        val globalRandomColorStartCB = rootView.findViewById<CheckBox>(R.id.global_random_color_start)
        globalRandomColorStartCB.isChecked = mStone?.globalRandomColorStartFlag!!
        globalRandomColorStartCB.setOnCheckedChangeListener { _, isChecked ->
            mStone?.globalRandomColorStartFlag = isChecked
        }
    }

    companion object {
        const val STONE_KEY = "stoneKey"
    }

}
