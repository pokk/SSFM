package taiwan.no1.app.ssfm.misc.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devrapid.kotlinknifer.getResColor
import com.example.jieyi.test.TimeUtils
import org.jetbrains.anko.*
import taiwan.no1.app.ssfm.R
import kotlin.properties.Delegates

/**
 * Circle image view with shadow.
 *
 * @author  jieyi
 * @since   6/19/17
 */
class RotatedCircleWithIconImageView: ViewGroup {
    companion object {
        private const val OUTER_PADDING = 30
        private const val INNER_PADDING = OUTER_PADDING + 30
        private const val TEXT_OFFSET = OUTER_PADDING - 10
        private const val START_TIME = 0
    }

    //region Test variable
    var temp_endtime = 20
    //endregion

    var src by Delegates.notNull<Int>()
    var foreIconInit = R.drawable.ic_play_arrow
    var foreIconClicked = R.drawable.ic_pause
    var currProgress = 0f
        set(value) {
            field = value
            this.intervalRate = this.currProgress / this.interval
        }
    var startTime = START_TIME
        set(value) {
            field = value
            this.interval = this.endTime - this.startTime
        }
    var endTime = START_TIME
        set(value) {
            field = value
            this.interval = this.endTime - this.startTime
        }
    var remainedTime = temp_endtime
    var interval by Delegates.notNull<Int>()
    var intervalRate by Delegates.notNull<Float>()

    //region Progress bar components.
    lateinit var rotatedCircleImageView: RotatedCircleImageView
        private set
    lateinit var circleSeekBar: CircularSeekBar
        private set
    lateinit var statusIcon: ImageView
        private set
    lateinit var timeLabels: List<TextView>
        private set
    //endregion

    //region Constructors
    constructor(context: Context): super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }
    //endregion

    fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.RotatedCircleWithIconImageView, defStyle, 0).also {
            this.src = it.getResourceId(R.styleable.RotatedCircleWithIconImageView_src, 0)
            this.foreIconInit = it.getInteger(R.styleable.RotatedCircleWithIconImageView_fore_icon, this.foreIconInit)
        }.recycle()

        // Setting variables.
        this.startTime = 0
        this.endTime = temp_endtime
        this.rotatedCircleImageView = RotatedCircleImageView(context).apply {
            imageResource = this@RotatedCircleWithIconImageView.src
            padding = INNER_PADDING
            setShadowRadius(0f)
            setBorderWidth(0f)
            onClickEvent = {
                val icon = if (this.isPauseState) {
                    this@RotatedCircleWithIconImageView.circleSeekBar.stopAnimator()
                    this@RotatedCircleWithIconImageView.foreIconInit
                }
                else {
                    this@RotatedCircleWithIconImageView.circleSeekBar.playAnimator(this@RotatedCircleWithIconImageView.remainedTime.toLong())
                    this@RotatedCircleWithIconImageView.foreIconClicked
                }
                // Changing the icon by the state.
                this@RotatedCircleWithIconImageView.statusIcon.setImageResource(icon)
                // Changing the state dependence state.
                this.isPauseState.not()
            }
        }
        this.circleSeekBar = (attrs?.let { CircularSeekBar(context, attrs, defStyle) } ?:
            CircularSeekBar(context)).apply {
            totalTime = this@RotatedCircleWithIconImageView.endTime
            padding = OUTER_PADDING
            onProgressChanged = { progress, remainedTime ->
                val passedTime = this@RotatedCircleWithIconImageView.endTime - remainedTime
                val accordingProcessTime = endTime - progress * this@RotatedCircleWithIconImageView.endTime / 100

                this@RotatedCircleWithIconImageView.remainedTime = remainedTime
                // Fixed the time isn't correct when clicking the non-stop the button of play and stop.
                if (accordingProcessTime != remainedTime) {
                    this@RotatedCircleWithIconImageView.remainedTime = accordingProcessTime
                }
                this@RotatedCircleWithIconImageView.timeLabels[0].text = TimeUtils.number2String(passedTime)
            }
            onProgressFinished = {
                this@RotatedCircleWithIconImageView.rotatedCircleImageView.stop()
                this@RotatedCircleWithIconImageView.statusIcon.setImageResource(this@RotatedCircleWithIconImageView.foreIconInit)
            }
        }
        // Add children view into this group.
        this.addView(this.circleSeekBar)
        this.addView(this.rotatedCircleImageView)
        this.statusIcon = imageView(this.foreIconInit)
        this.timeLabels = listOf(
            textView(TimeUtils.number2String(this.startTime)).apply {
                textColor = getResColor(R.color.colorDarkGray)
            },
            textView(TimeUtils.number2String(this.endTime)).apply {
                textColor = getResColor(R.color.colorDarkGray)
            })
        this.currProgress = 0f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Measure all of children's width & height.
        this.measureChildren(widthMeasureSpec, heightMeasureSpec)
        // Measure width & height of this view_group's layout(layout_width or layout_height will be `match_parent`
        // no matter what we set `wrap_content` or `match_patent` when we're using getDefaultSize).
        // We'll reset this method by another way for achieving `wrap_content`.
        this.setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val w = this.width
        val h = this.height

        (0 until this.childCount).forEach {
            val childW = this.getChildAt(it).measuredWidth
            val childH = this.getChildAt(it).measuredHeight
            val px = pivotX.toInt()
            val py = pivotX.toInt()

            val (l, t, r, b) = when (it) {
            // Circular seek bar.
                0 -> Rect(0, 0, childW, childH)
            // 1: Inner image view and 2: Status icon.
                1 -> Rect(px - childW / 2 + INNER_PADDING,
                    py - childH / 2 + INNER_PADDING,
                    px + childW / 2 - INNER_PADDING,
                    py + childH / 2 - INNER_PADDING)
                2 -> Rect(px - childW / 2,
                    py - childH / 2,
                    px + childW / 2,
                    py + childH / 2)
            // Two text views.
                3 -> Rect(w / 4 - childW / 2,
                    (h - childH - TEXT_OFFSET),
                    w / 4 + childW / 2,
                    (h - TEXT_OFFSET))
                4 -> Rect(w / 4 * 3 - childW / 2,
                    (h - childH - TEXT_OFFSET),
                    w / 4 * 3 + childW / 2,
                    (h - TEXT_OFFSET))
                else -> Rect(0, 0, 0, 0)
            }

            this.getChildAt(it).layout(l, t, r, b)
        }
    }

    data class Rect(val l: Int, val t: Int, val r: Int, val b: Int)
}