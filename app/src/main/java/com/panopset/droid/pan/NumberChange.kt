package com.panopset.droid.pan

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.panopset.droid.games.ripon.R
import com.panopset.droid.games.ripon.db.ChangeBoundsDef
import com.panopset.droid.games.ripon.db.StoneRoomDatabase

class NumberChange @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(
        context,
        attrs,
        defStyleAttr
    ) {

    var parentView: View? = null
    var parentFragment: Fragment? = null
    val uniqueID: Int = nextID++

    private var ncLblTextView: TextView
    private var attrLbl: String
    private var changeBounds: ChangeBoundsDef = StoneRoomDatabase.DEFAULT_CHANGE_BOUNDS
    init {
        inflate(context, R.layout.number_change, this)
        ncLblTextView = findViewById(R.id.nc_lbl)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.NumberChange)
        attrLbl = attributes.getString(R.styleable.NumberChange_nclbl).toString()
        val attrMaxLimit = attributes.getInt(R.styleable.NumberChange_ncmax, 9923)



        ncLblTextView.text = attrLbl

        val ncBtn = findViewById<Button>(R.id.nc_btn)

        ncBtn.setOnClickListener {
            val intent = Intent(it.context, ActivityDefineNumberChange::class.java)
            changeBounds.max = attrMaxLimit
            ActivityDefineNumberChange.changeBounds = changeBounds
            parentFragment?.startActivityForResult(intent, uniqueID)
        }
        attributes.recycle()
    }

    fun setChangeBounds(changeBounds: ChangeBoundsDef) {
        this.changeBounds = changeBounds
        ncLblTextView.invalidate()
        updateLabel()
    }

    fun getChangeBounds(): ChangeBoundsDef {
        return this.changeBounds
    }

    private fun updateLabel() {
        ncLblTextView.text = String.format(
            "%s %d, %d-%d, %d", attrLbl,
            changeBounds.start, changeBounds.min, changeBounds.max, changeBounds.delay
        )
    }

    companion object {
        private var nextID = 0
    }
}
