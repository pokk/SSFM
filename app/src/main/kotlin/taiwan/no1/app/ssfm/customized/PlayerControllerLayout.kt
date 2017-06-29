package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.logw
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.imageButton
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   6/28/17
 */
class PlayerControllerLayout: ViewGroup {
    val propertis: ImageButton.() -> Unit = {
        this.backgroundColor = Color.TRANSPARENT
        this.scaleType = ImageView.ScaleType.CENTER
    }
    val listImageButtons = listOf(imageButton(R.drawable.ic_repeat, propertis),
        imageButton(R.drawable.ic_skip_previous),
        imageButton(R.drawable.ic_play_circle),
        imageButton(R.drawable.ic_skip_next),
        imageButton(R.drawable.ic_shuffle))
    var isPlayingState = false
    var isRepeatingAll = true

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
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        logw(MeasureSpec.toString(widthMeasureSpec), MeasureSpec.toString(heightMeasureSpec))

        this.measureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (5 < this.childCount)
            return

        logd(l, t, r, b)

        this.listImageButtons.forEachWithIndex { index, _ ->
            val childView = this.getChildAt(index)
            layoutParams = childView.layoutParams
            logd(childView, layoutParams)
            if (View.GONE == childView.visibility)
                return@forEachWithIndex

            logw(childView.measuredWidth, childView.measuredHeight)
        }
    }

    fun setRepeatOnClick(listener: (ImageButton) -> Unit) = this.listImageButtons[0].onClick {
        listener(it as ImageButton)
        this@PlayerControllerLayout.isRepeatingAll = !this@PlayerControllerLayout.isRepeatingAll
        it.imageResource = if (this@PlayerControllerLayout.isRepeatingAll) R.drawable.ic_repeat else R.drawable.ic_repeat_one
    }

    fun setPreviousOnClick(listener: (ImageButton) -> Unit) = this.listImageButtons[1].onClick { listener(it as ImageButton) }

    fun setPlayOnClick(listener: (ImageButton) -> Unit) = this.listImageButtons[2].onClick {
        listener(it as ImageButton)
        this@PlayerControllerLayout.isPlayingState = !this@PlayerControllerLayout.isPlayingState
        it.imageResource = if (this@PlayerControllerLayout.isPlayingState) R.drawable.ic_play_circle else R.drawable.ic_pause_circle
    }

    fun setNextOnClick(listener: (ImageButton) -> Unit) = this.listImageButtons[3].onClick { listener(it as ImageButton) }

    fun setShuffleOnClick(listener: (ImageButton) -> Unit) = this.listImageButtons[4].onClick { listener(it as ImageButton) }
}