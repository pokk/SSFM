package taiwan.no1.app.ssfm.customized

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
        imageButton(R.drawable.ic_skip_previous, propertis),
        imageButton(R.drawable.ic_play_circle) {
            this.backgroundColor = Color.TRANSPARENT
            this.padding = 0
            this.scaleType = ImageView.ScaleType.FIT_CENTER
        },
        imageButton(R.drawable.ic_skip_next, propertis),
        imageButton(R.drawable.ic_shuffle, propertis))
    val listBtnListeners = mutableListOf({ imagebtn: ImageButton -> },
        { imagebtn: ImageButton -> },
        { imagebtn: ImageButton -> },
        { imagebtn: ImageButton -> },
        { imagebtn: ImageButton -> })
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
        val unitWidth = MeasureSpec.getSize(widthMeasureSpec) / 6

        // Set the each of children components size.
        this.listImageButtons.forEachWithIndex { index, _ ->
            val childWidth = if (2 == index) unitWidth * 2 else unitWidth
            this.getChildAt(index).measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY))
        }
        // Pick the highest component's height.
        val maxHeight = this.listImageButtons.map { it.height }.max() ?: MeasureSpec.getSize(heightMeasureSpec)
        // Set this layout size.
        this.setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        // Not allow to add more components into this view group.
        if (5 < this.childCount)
            return

        // left, top, right, bottom are this layout's size.
        val layoutWidth = right - left
        val layoutHeight = bottom - top

        // repeat button
        val childViewRepeat = this.getChildAt(0)
        childViewRepeat.layout(0, 0, childViewRepeat.measuredWidth, childViewRepeat.measuredHeight)
        // previous button
        val childViewPrevious = this.getChildAt(1)
        childViewPrevious.layout(childViewRepeat.right,
            0,
            childViewRepeat.right + childViewPrevious.measuredWidth,
            childViewPrevious.measuredHeight)
        // play button
        val childViewPlay = this.getChildAt(2)
        childViewPlay.layout(layoutWidth / 2 - childViewPlay.measuredWidth / 2,
            0,
            layoutWidth / 2 + childViewPlay.measuredWidth / 2,
            childViewPlay.measuredHeight)
        // next button
        val childViewNext = this.getChildAt(3)
        childViewNext.layout(childViewPlay.right,
            0,
            childViewPlay.right + childViewNext.measuredWidth,
            childViewNext.measuredHeight)
        // shuffle button
        val childViewShuffle = this.getChildAt(4)
        childViewShuffle.layout(childViewNext.right,
            0,
            childViewNext.right + childViewShuffle.measuredWidth,
            childViewShuffle.measuredHeight)
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