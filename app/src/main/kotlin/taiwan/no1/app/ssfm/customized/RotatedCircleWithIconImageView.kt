package taiwan.no1.app.ssfm.customized

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
        private const val OUTTER_PADDING = 30
        private const val INNER_PADDING = OUTTER_PADDING + 50
        private const val TEXT_OFFSET = OUTTER_PADDING - 10
        private const val START_TIME = 0
    }

    //region Test variable
    var temp_endtime = 170
    //endregion

    var src by Delegates.notNull<Int>()
    var foreIconInit = R.drawable.ic_play_arrow
    var foreIconClicked = R.drawable.ic_pause
    var currProgress = 0f
        set(value) {
            field = value
            this.intervalRate = this.currProgress / this.interval
            this.timeLabels[0].text = TimeUtils.number2String(field.toInt())
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

    //region Progress bar variables.
    //endregion

    constructor(context: Context): super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        init(context, attrs, defStyle)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.RotatedCircleWithIconImageView, defStyle, 0).also {
            this.src = it.getResourceId(R.styleable.RotatedCircleWithIconImageView_src, 0)
            this.foreIconInit = it.getInteger(R.styleable.RotatedCircleWithIconImageView_fore_icon, this.foreIconInit)
        }.recycle()

        this.startTime = 0
        this.endTime = temp_endtime
        this.rotatedCircleImageView = RotatedCircleImageView(context).apply {
            imageResource = this@RotatedCircleWithIconImageView.src
            padding = INNER_PADDING
            setShadowRadius(0f)
            setBorderWidth(0f)
        }
        this.circleSeekBar = (attrs?.let { CircularSeekBar(context, attrs, defStyle) } ?:
            CircularSeekBar(context)).apply {
            padding = OUTTER_PADDING
        }
        this.addView(this.circleSeekBar)
        this.addView(this.rotatedCircleImageView)
        this.statusIcon = imageView(this.foreIconInit)
        this.timeLabels = listOf(
            textView(TimeUtils.number2String(this.startTime)).apply {
                textColor = getResColor(R.color.colorWhite)
                backgroundColor = getResColor(R.color.colorGreen)
            },
            textView(TimeUtils.number2String(this.endTime)).apply {
                textColor = getResColor(R.color.colorDarkGray)
                backgroundColor = getResColor(R.color.colorGreen)

            })
        this.currProgress = 0f
    }

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Measure all of children's width & height.
        this.measureChildren(widthMeasureSpec, heightMeasureSpec)
        // Measure width & height of this view_group's layout(layout_width or layout_height will be `match_parent`
        // no matter what we set `wrap_content` or `match_patent` when we're using getDefaultSize).
        // We'll reset this method by another way for achieving `wrap_content`.
        this.setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val w = this.width
        val h = this.height

        (0 until this.childCount).forEach {
            val childW = this.getChildAt(it).measuredWidth
            val childH = this.getChildAt(it).measuredHeight

            when (it) {
            // Circular seek bar.
                0 -> this.getChildAt(it).layout(0, 0, childW, childH)
            // 1: Inner image view and 2: Status icon.
                1 -> this.getChildAt(it).layout(pivotX.toInt() - childW / 2 + INNER_PADDING,
                    pivotY.toInt() - childH / 2 + INNER_PADDING,
                    pivotX.toInt() + childW / 2 - INNER_PADDING,
                    pivotY.toInt() + childH / 2 - INNER_PADDING)
                2 -> {
                }
            // Two text views.
                3 -> this.getChildAt(it).layout(w / 4 - childW / 2,
                    (h - childH - TEXT_OFFSET),
                    w / 4 + childW / 2,
                    (h - TEXT_OFFSET))
                4 -> this.getChildAt(it).layout(w / 4 * 3 - childW / 2,
                    (h - childH - TEXT_OFFSET),
                    w / 4 * 3 + childW / 2,
                    (h - TEXT_OFFSET))
            }
        }
    }
}