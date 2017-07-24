package taiwan.no1.app.ssfm.misc.widgets

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
import org.jetbrains.anko.padding
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.pattern.decorate.NormalPlayerButtonDecorator
import taiwan.no1.app.ssfm.pattern.decorate.PlayPlayerButtonDecorator
import taiwan.no1.app.ssfm.pattern.decorate.PlayerButtonDecorator
import taiwan.no1.app.ssfm.pattern.decorate.RepeatPlayerButtonDecorator
import taiwan.no1.app.ssfm.pattern.state.IControllerButtonState
import taiwan.no1.app.ssfm.pattern.state.PauseState
import taiwan.no1.app.ssfm.pattern.state.RepeatState

/**
 * Player controller panel.
 *
 * @author  jieyi
 * @since   6/28/17
 */
class PlayerControllerLayout: ViewGroup {
    val properties: ImageButton.() -> Unit = {
        this.backgroundColor = Color.BLACK
        this.scaleType = ImageView.ScaleType.FIT_CENTER
        this.padding = 20
    }
    val listImageButtons = listOf(imageButton(), imageButton(), imageButton(), imageButton(), imageButton())
    val listBtnListeners = mutableListOf(
        { _: ImageButton -> },
        { _: ImageButton -> },
        { _: ImageButton -> },
        { _: ImageButton -> },
        { _: ImageButton -> })
    val stateSetFunction = { state: IControllerButtonState ->
        val classname = Thread.currentThread().stackTrace[5].className
        val interfaces = Class.forName(classname).interfaces

        if (interfaces.isEmpty() or ("IControllerButtonState" != interfaces[0].simpleName))
            error("Can not set state by yourself.")

        state
    }
    var statePlayMode: IControllerButtonState = PauseState()
        set(value) {
            field = this.stateSetFunction(value)
        }
    var stateRecycleMode: IControllerButtonState = RepeatState()
        set(value) {
            field = this.stateSetFunction(value)
        }
    lateinit var listDecoratedButtons: List<PlayerButtonDecorator>

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
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Separate 6 blocks, center is occupied 2 blocks.
        val unitWidth = (MeasureSpec.getSize(widthMeasureSpec) - this.paddingStart - this.paddingEnd) / 6

        // Set the each of children components size.
        this.listImageButtons.forEachWithIndex { index, _ ->
            val childWidth = if (2 == index) unitWidth.times(2) else unitWidth
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
        // left, top, right, bottom are this layout's size.
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

        this.listDecoratedButtons = this.listImageButtons.mapIndexed { index, btn ->
            when (index) {
                0 -> RepeatPlayerButtonDecorator(btn)
                1 -> NormalPlayerButtonDecorator(btn) {
                    this.imageResource = R.drawable.selector_controller_previous
                    this.padding = 20
                }
            // TODO: 7/4/17 This init icon won't be center in the beginning.
                2 -> PlayPlayerButtonDecorator(btn)
                3 -> NormalPlayerButtonDecorator(btn) {
                    this.imageResource = R.drawable.selector_controller_next
                    this.padding = 20
                }
                4 -> NormalPlayerButtonDecorator(btn) {
                    this.imageResource = R.drawable.selector_controller_shuffle
                    this.padding = 20
                }
                else -> NormalPlayerButtonDecorator(btn)
            }
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