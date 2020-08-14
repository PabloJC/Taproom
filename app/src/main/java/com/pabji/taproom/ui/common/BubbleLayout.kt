package com.pabji.taproom.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.pabji.taproom.R


class BubbleLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var quantityTextView: TextView?

    var quantity: String
        get() = quantityTextView?.text.toString()
        set(value) {
            quantityTextView?.text = value
        }

    enum class BubbleBackground(val res: Int) {
        PURPLE(R.drawable.circle_purple_background),
        ORANGE(R.drawable.circle_orange_background)
    }

    init {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.BubbleLayout, 0, 0
        )
        val labelText = a.getString(R.styleable.BubbleLayout_bubbleTitle)
        val backgroundRes =
            BubbleBackground.values()[a.getInt(R.styleable.BubbleLayout_bubbleColor, 0)].res
        a.recycle()

        orientation = VERTICAL
        gravity = Gravity.CENTER
        background = ResourcesCompat.getDrawable(resources, backgroundRes, null)

        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.view_circle_indicator, this, true)

        val title = getChildAt(0) as TextView
        title.text = labelText

        quantityTextView = getChildAt(1) as? TextView
    }

}