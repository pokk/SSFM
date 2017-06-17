package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import taiwan.no1.app.ssfm.R

/**
 * For inputting customize special font.
 */
class FontTextView: TextView {
    constructor(context: Context): super(context) {
        this.init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        this.init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.FontTextView).also {
            it.getString(R.styleable.FontTextView_font)?.let {
                this.typeface = Typeface.createFromAsset(this.context.assets, "fonts/$it")
            }
        }.recycle()
    }
}
