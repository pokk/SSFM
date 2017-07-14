package taiwan.no1.app.ssfm.customized

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.devrapid.kotlinknifer.getResColor
import com.example.jieyi.test.TimeUtils
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
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
        private const val INNER_PADDING = OUTER_PADDING.toInt() + 30
        private const val TEXT_OFFSET = OUTER_PADDING.toInt() - 10
        private const val START_ANGLE = 140f
        private const val END_ANGLE = 260f
    }

    var src = R.drawable.sample_jieyi_icon
    var foreIcon = R.drawable.ic_play_arrow
    var currProgress = 0f
        set(value) {
            field = value
            this.intervalRate = this.currProgress / this.interval
            this.timeLabels[0].text = TimeUtils.number2String(field.toInt())
        }
    var startTime = 0
        set(value) {
            field = value
            this.interval = this.endTime - this.startTime
        }
    var endTime = 0
        set(value) {
            field = value
            this.interval = this.endTime - this.startTime
        }
    var interval by Delegates.notNull<Int>()
    var intervalRate by Delegates.notNull<Float>()

    lateinit var rotatedCircleImageView: RotatedCircleImageView
        private set
    lateinit var timeLabels: List<TextView>
        private set
    lateinit var controlButton: ImageView
        private set
    lateinit var timeControlButton: ImageView
        private set
    // Progress bar components.
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
    private val pm by lazy { PathMeasure(Path().also { it.addArc(this.rectProgress, START_ANGLE, END_ANGLE) }, false) }
    private val rectProgress by lazy {
        RectF(OUTER_PADDING, OUTER_PADDING, this.width - OUTER_PADDING, this.height - OUTER_PADDING)
    }
    private var pos = floatArrayOf(0f, 0f)
    private var tan = floatArrayOf(0f, 0f)

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
            this.src = it.getInteger(R.styleable.RotatedCircleWithIconImageView_src, this.src)
            this.foreIcon = it.getInteger(R.styleable.RotatedCircleWithIconImageView_fore_icon, this.foreIcon)
        }.recycle()

        this.startTime = 0
        this.endTime = 170
        this.rotatedCircleImageView = RotatedCircleImageView(context).apply {
            imageResource = R.drawable.sample_jieyi_icon
        }
        this.addView(this.rotatedCircleImageView)
        this.timeControlButton = imageView()
        this.controlButton = imageView(R.drawable.ic_play_arrow)
        this.timeLabels = listOf(
            textView(TimeUtils.number2String(this.startTime)).apply {
                textColor = R.color.colorWhite
                backgroundColor = R.color.colorGreen
            },
            textView(TimeUtils.number2String(this.endTime)).apply {
                textColor = R.color.colorDarkGray
                backgroundColor = R.color.colorGreen
            })

        this.timeControlButton.onClick {
            if (currProgress <= interval)
                invalidate()
        }
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
        // This is key of all moving objects.
        if (currProgress < interval)
            this.currProgress += 1

        val pathWillProgress = Path().also {
            it.addArc(this.rectProgress,
                START_ANGLE + END_ANGLE,
                -((this.interval - this.currProgress) / this.interval * END_ANGLE))
        }
        val pathDoneProgress = Path().also {
            it.addArc(this.rectProgress, START_ANGLE, this.intervalRate * END_ANGLE)
        }

        pm.getPosTan(pm.length * this.intervalRate, this.pos, this.tan)

        canvas.drawPath(pathWillProgress, this.p1)
        canvas.drawPath(pathDoneProgress, this.p2)
        this.timeControlButton.x = pos[0] - this.timeControlButton.width / 2
        this.timeControlButton.y = pos[1] - this.timeControlButton.height / 2
    }
}
