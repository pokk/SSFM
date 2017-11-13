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
class FontTextView @JvmOverloads constructor(context: Context,
                                             attrs: AttributeSet? = null,
                                             defStyleAttr: Int = 0) :
    TextView(context, attrs, defStyleAttr) {
    init {
        context.obtainStyledAttributes(attrs, R.styleable.FontTextView, defStyleAttr, 0).also {
            it.getString(R.styleable.FontTextView_textFont).let {
                typeface = Typeface.createFromAsset(context.assets, "fonts/$it")
            }
        }.recycle()
    }
}
