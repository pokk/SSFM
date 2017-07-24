package taiwan.no1.app.ssfm.misc.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import taiwan.no1.app.ssfm.R

/**
 * For inputting customize special font.
 *
 * @author  jieyi
 * @since   6/14/17
 */
class FontTextView: TextView {
    constructor(context: Context): super(context) {
        this.init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init(context, attrs, defStyleAttr)
    }


    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.FontTextView, defStyleAttr, 0).also {
            it.getString(R.styleable.FontTextView_font).let {
                this.typeface = Typeface.createFromAsset(this.context.assets, "fonts/$it")
            }
        }.recycle()
    }
}
