package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.devrapid.kotlinknifer.logw
import org.jetbrains.anko.imageButton
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   6/28/17
 */
class PlayerControllerLayout: ViewGroup {
    val listImageButtons = listOf(imageButton(R.drawable.ic_repeat),
        imageButton(R.drawable.ic_skip_previous),
        imageButton(R.drawable.ic_play_circle),
        imageButton(R.drawable.ic_skip_next),
        imageButton(R.drawable.ic_shuffle))

    init {
    }

    constructor(context: Context): super(context) {
        this.init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init(context, attrs, defStyleAttr)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
//        this.listImageButtons.forEach(this::addView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        logw(MeasureSpec.toString(widthMeasureSpec), MeasureSpec.toString(heightMeasureSpec))

        (0..this.childCount - 1).forEach {
            this.measureChild(this.getChildAt(it), widthMeasureSpec, heightMeasureSpec)
        }

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childrenCount = this.childCount
    }
}