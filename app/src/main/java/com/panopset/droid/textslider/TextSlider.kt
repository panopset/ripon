package com.panopset.droid.textslider

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.panopset.droid.games.ripon.R

class TextSlider @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var nsLbl: TextView
    private lateinit var nsText: EditText
    private lateinit var nsSlide: SeekBar

    private var attrLbl: String? = ""
    private var attrMin: Int = 0
    private var attrMax: Int = 200

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        inflate(context, R.layout.textsliderlayout, this)
        initFromCustomAttrs(attrs)
        linkSlider()
        linkTextView()
    }

    private fun initFromCustomAttrs(attrs: AttributeSet?) {
        findViews()
        val textSliderAttrs = context.obtainStyledAttributes(attrs, R.styleable.TextSlider)
        attrMin = textSliderAttrs.getInt(R.styleable.TextSlider_min, 0)
        attrMax = textSliderAttrs.getInt(R.styleable.TextSlider_max, 100)
        attrLbl = textSliderAttrs.getString(R.styleable.TextSlider_lbl)
        nsLbl.text = attrLbl
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nsSlide.min = attrMin
        }
        nsSlide.max = attrMax
        nsText.filters = arrayOf(InputFilterRange(IntRange(0, attrMax)))
        textSliderAttrs.recycle()
    }

    private fun linkSlider() {
        nsSlide.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    nsText.setText(String.format("%d", progress))
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun linkTextView() {
        nsText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                try {
                    val intValue = Integer.parseInt(editable.toString())
                    nsSlide.progress = intValue
                } catch (ex: NumberFormatException) {
                }
            }
        })
    }

    fun setValue(value: Int) {
        nsText.setText(String.format("%d", value))
    }

    fun setMaximum(value: Int) {
        attrMax = value
    }

    fun getValue(): Int {
        return nsSlide.progress
    }

    private fun findViews() {
        nsLbl = findViewById(R.id.nslbl)
        nsText = findViewById(R.id.nstext)
        nsSlide = findViewById(R.id.nsslide)
    }
}
