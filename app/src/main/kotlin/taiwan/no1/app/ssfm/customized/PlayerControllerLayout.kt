package taiwan.no1.app.ssfm.customized

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.imageButton
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.padding
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.R

/**
 * Player controller panel.
 *
 * @author  jieyi
 * @since   6/28/17
 */
class PlayerControllerLayout: ViewGroup {
    val properties: ImageButton.() -> Unit = {
        this.backgroundColor = Color.TRANSPARENT
        this.scaleType = ImageView.ScaleType.FIT_CENTER
        this.padding = 20
    }
    val listImageButtons = listOf(
        imageButton(R.drawable.selector_controller_repeat, properties),
        imageButton(R.drawable.selector_controller_previous, properties),
        imageButton(R.drawable.selector_controller_play) {
            this.backgroundColor = Color.TRANSPARENT
            this.scaleType = ImageView.ScaleType.FIT_CENTER
            this.padding = 0
        },
        imageButton(R.drawable.selector_controller_next, properties),
        imageButton(R.drawable.selector_controller_shuffle, properties))
    val listBtnListeners = mutableListOf(
        { _: ImageButton -> },
        { _: ImageButton -> },
        { _: ImageButton -> },
        { _: ImageButton -> },
        { _: ImageButton -> })
    var isPlayingState = false
    var isRepeatingAll = true

    constructor(context: Context): super(context) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init()
    }

    fun init() {
        // Set each of image buttons' listener.
        this.listImageButtons.zip(this.listBtnListeners).forEachWithIndex { index, (btn, listener) ->
            btn.onClick {
                listener(it as ImageButton)
                when (index) {
                    0 -> {
                        this@PlayerControllerLayout.isRepeatingAll = !this@PlayerControllerLayout.isRepeatingAll
                        it.imageResource = if (this@PlayerControllerLayout.isRepeatingAll) R.drawable.ic_repeat else R.drawable.ic_repeat_one
                    }
                    2 -> {
                        this@PlayerControllerLayout.isPlayingState = !this@PlayerControllerLayout.isPlayingState
                        it.imageResource = if (this@PlayerControllerLayout.isPlayingState) R.drawable.ic_pause_circle else R.drawable.ic_play_circle
                    }
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Separate 6 blocks, center is occupied 2 blocks.
        val unitWidth = (MeasureSpec.getSize(widthMeasureSpec) - this.paddingStart - this.paddingEnd) / 6

        // Set the each of children components size.
        this.listImageButtons.forEachWithIndex { index, _ ->
            val childWidth = if (2 == index) unitWidth * 2 else unitWidth
            this.getChildAt(index).measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childWidth - this.paddingTop - this.paddingBottom, MeasureSpec.EXACTLY))
        }
        // Pick the highest component's height and plus left & right margin.
        val maxHeight = this.listImageButtons.map { it.height }.max()?.plus(this.paddingTop)?.plus(this.paddingBottom) ?:
            MeasureSpec.getSize(heightMeasureSpec)
        // Set this layout size.
        this.setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // left, top, right, bottom are this layout's size.
        // Not allow to add more components into this view group.
        if (5 < this.childCount)
            return

        val layoutWidth = right - left
        // b: bottom, t: top
        val b = this.listImageButtons.map { it.measuredHeight }.max()?.times(0.7)?.minus(this.paddingTop)?.
            minus(this.paddingBottom)?.toInt() ?: bottom
        val t = this.paddingTop
        var prev: ImageButton = imageButton()

        this.listImageButtons.forEachWithIndex { index, btn ->
            // l: left, r: right
            val (l, r) = when (index) {
                0 -> Pair(this.paddingLeft, this.paddingLeft + btn.measuredWidth)
                2 -> Pair(layoutWidth / 2 - btn.measuredWidth / 2, layoutWidth / 2 + btn.measuredWidth / 2)
                1, 3, 4 -> Pair(prev.right, prev.right + btn.measuredWidth)
                else -> Pair(0, 0)
            }
            this.getChildAt(index).layout(l, t, r, b)
            prev = btn
        }
    }

    fun setRepeatOnClick(listener: (ImageButton) -> Unit) {
        this.listBtnListeners[0] = listener
    }

    fun setPreviousOnClick(listener: (ImageButton) -> Unit) {
        this.listBtnListeners[1] = listener
    }

    fun setPlayOnClick(listener: (ImageButton) -> Unit) {
        this.listBtnListeners[2] = listener
    }

    fun setNextOnClick(listener: (ImageButton) -> Unit) {
        this.listBtnListeners[3] = listener
    }

    fun setShuffleOnClick(listener: (ImageButton) -> Unit) {
        this.listBtnListeners[4] = listener
    }
}