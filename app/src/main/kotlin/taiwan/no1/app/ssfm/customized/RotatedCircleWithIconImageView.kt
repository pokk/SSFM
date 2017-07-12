package taiwan.no1.app.ssfm.customized

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.imageView
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textView
import taiwan.no1.app.ssfm.R

class RotatedCircleWithIconImageView: ViewGroup {
    var src = R.drawable.sample_jieyi_icon
    var fore_icon = R.drawable.ic_play_arrow

    lateinit var rotatedCircleImageView: RotatedCircleImageView
        private set
    lateinit var timeLabels: List<TextView>
        private set
    lateinit var controlButton: ImageView
        private set
    lateinit var timeControlButton: ImageView
        private set

    private val innerPadding = 40
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
    private var currProgress = 0f
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
            this.fore_icon = it.getInteger(R.styleable.RotatedCircleWithIconImageView_fore_icon, this.fore_icon)
        }.recycle()

        this.rotatedCircleImageView = RotatedCircleImageView(context).apply {
            //            imageResource = R.drawable.sample_jieyi_icon
        }
        this.addView(this.rotatedCircleImageView)
        this.timeControlButton = imageView()
        this.controlButton = imageView(R.drawable.ic_play_arrow)
        this.timeLabels = listOf(textView("00:00").apply {
            textColor = R.color.colorWhite
            backgroundColor = R.color.colorGreen
        }, textView("03:14").apply {
            textColor = R.color.colorDarkGray
            backgroundColor = R.color.colorGreen
        })
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
        val size = 100

        (0 until this.childCount).forEach {
            val childW = this.getChildAt(it).measuredWidth
            val childH = this.getChildAt(it).measuredHeight

            when (it) {
                1 -> this.getChildAt(it).layout(0, 0, size, size)
                0, 2 -> this.getChildAt(it).layout(w / 2 - childW / 2,
                    h / 2 - childH / 2,
                    w / 2 + childW / 2,
                    h / 2 + childH / 2)
            // Two text views.
                3 -> this.getChildAt(it).layout(w / 4 - childW / 2, h - childH, w / 4 + childW / 2, h)
                4 -> this.getChildAt(it).layout(w / 4 * 3 - childW / 2, h - childH, w / 4 * 3 + childW / 2, h)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val w = this.width.toFloat()
        val h = this.height.toFloat()
        val padding = 40
        val start = 140f
        val end = 260f

        // This is key of all moving objects.
        currProgress += 1

        val rect = RectF(0f + padding, 0f + padding, w - padding, h - padding)
        val p = Path().also { it.addArc(rect, currProgress + start, end - currProgress) }
        val pm = PathMeasure(Path().also { it.addArc(rect, start, end) }, false)
        val pp = Path().also { it.addArc(rect, start, currProgress) }

        pm.getPosTan(pm.length * currProgress / end, pos, tan)

        canvas.drawPath(p, this.p1)
        canvas.drawPath(pp, this.p2)
        this.timeControlButton.x = pos[0] - this.timeControlButton.width / 2
        this.timeControlButton.y = pos[1] - this.timeControlButton.height / 2

        if (currProgress <= end)
            invalidate()
    }
}
