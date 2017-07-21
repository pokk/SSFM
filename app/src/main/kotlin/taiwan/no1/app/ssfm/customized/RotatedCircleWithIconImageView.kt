package taiwan.no1.app.ssfm.customized

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.TextView
import com.devrapid.kotlinknifer.getResColor
import com.example.jieyi.test.TimeUtils
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView
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
        private const val OUTER_PADDING = 40f
        private const val INNER_PADDING = OUTER_PADDING.toInt() + 50
        private const val TEXT_OFFSET = OUTER_PADDING.toInt() - 10
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
    lateinit var timeLabels: List<TextView>
        private set
    //endregion

    //region Progress bar variables.
    private val rectProgress by lazy {
        RectF(OUTER_PADDING, OUTER_PADDING, this.width - OUTER_PADDING, this.height - OUTER_PADDING)
    }
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
            setShadowRadius(0f)
            setBorderWidth(0f)
        }
        this.circleSeekBar = (attrs?.let { CircularSeekBar(context, attrs, defStyle) } ?:
            CircularSeekBar(context)).apply {
        }
        this.addView(this.rotatedCircleImageView)
        this.addView(this.circleSeekBar)
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
        this.getChildAt(0).measure(MeasureSpec.makeMeasureSpec(this.width - 2 * INNER_PADDING, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(this.height - 2 * INNER_PADDING, MeasureSpec.EXACTLY))
        // Measure width & height of this view_group's layout(layout_width or layout_height will be `match_parent`
        // no matter what we set `wrap_content` or `match_patent` when we're using getDefaultSize).
        // We'll reset this method by another way for achieving `wrap_content`.
        this.setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec),
            getDefaultSize(suggestedMinimumHeight, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val w = this.width
        val h = this.height
        val size = 100

        (0 until this.childCount).forEach {
            val childW = this.getChildAt(it).measuredWidth
            val childH = this.getChildAt(it).measuredHeight

            when (it) {
            // Inner image view.
                0 -> this.getChildAt(it).layout(w / 2 - childW / 2,
                    h / 2 - childH / 2,
                    w / 2 + childW / 2,
                    h / 2 + childH / 2)
            // Circular seek bar.
                1 -> this.getChildAt(it).layout(0, 0, size, size)
            // Two text views.
                2 -> this.getChildAt(it).layout(w / 4 - childW / 2,
                    (h - childH - TEXT_OFFSET),
                    w / 4 + childW / 2,
                    (h - TEXT_OFFSET))
                3 -> this.getChildAt(it).layout(w / 4 * 3 - childW / 2,
                    (h - childH - TEXT_OFFSET),
                    w / 4 * 3 + childW / 2,
                    (h - TEXT_OFFSET))
            }
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
    }
}