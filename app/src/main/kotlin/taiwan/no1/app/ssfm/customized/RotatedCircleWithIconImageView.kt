package taiwan.no1.app.ssfm.customized

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.jieyi.test.TimeUtils
import org.jetbrains.anko.*
import taiwan.no1.app.ssfm.R
import java.util.*
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
        private const val START_ANGLE = 140f
        private const val END_ANGLE = 260f
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
    lateinit var timeLabels: List<TextView>
        private set
    lateinit var controlButton: ImageView
        private set
    lateinit var timeControlButton: ImageView
        private set
    //endregion

    //region Progress bar variables.
    private val p1 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = R.color.colorBlack
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 30f
            style = Paint.Style.STROKE
        }
    }
    private val p2 by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG).apply {
            color = R.color.colorRed
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 30f
            style = Paint.Style.STROKE
        }
    }
    private val pmProgress by lazy {
        PathMeasure(Path().also { it.addArc(this.rectProgress, START_ANGLE, END_ANGLE) },
            false)
    }
    private val rectProgress by lazy {
        RectF(OUTER_PADDING, OUTER_PADDING, this.width - OUTER_PADDING, this.height - OUTER_PADDING)
    }
    private var pos = floatArrayOf(0f, 0f)
    private var tan = floatArrayOf(0f, 0f)
    private var timer by Delegates.notNull<Timer>()
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
        this.addView(this.rotatedCircleImageView)
        // TODO(jieyi): 7/16/17 Find a good circle button controller.
        this.timeControlButton = imageView()
        this.controlButton = imageView(this.foreIconInit)
        this.timeLabels = listOf(
            textView(TimeUtils.number2String(this.startTime)).apply {
                textColor = R.color.colorWhite
                backgroundColor = R.color.colorGreen
            },
            textView(TimeUtils.number2String(this.endTime)).apply {
                textColor = R.color.colorDarkGray
                backgroundColor = R.color.colorGreen

            })
        this.rotatedCircleImageView.onClickEvent = {
            this.controlButton.imageResource = if (it.isPauseState) this.foreIconInit else this.foreIconClicked
            // TODO(jieyi): 7/16/17 timer is not implemented.
        }
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
                1 -> this.getChildAt(it).layout(0, 0, size, size)
                0, 2 -> this.getChildAt(it).layout(w / 2 - childW / 2,
                    h / 2 - childH / 2,
                    w / 2 + childW / 2,
                    h / 2 + childH / 2)
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

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
//        this.currProgress += 1

        val pathWillProgress = Path().also {
            it.addArc(this.rectProgress,
                START_ANGLE + END_ANGLE,
                -((this.interval - this.currProgress) / this.interval * END_ANGLE))
        }
        val pathDoneProgress = Path().also {
            it.addArc(this.rectProgress, START_ANGLE, this.intervalRate * END_ANGLE)
        }

        this.pmProgress.getPosTan(pmProgress.length * this.intervalRate, this.pos, this.tan)

        canvas.drawPath(pathWillProgress, this.p1)
        canvas.drawPath(pathDoneProgress, this.p2)
        this.timeControlButton.x = pos[0] - this.timeControlButton.width / 2
        this.timeControlButton.y = pos[1] - this.timeControlButton.height / 2
    }
}