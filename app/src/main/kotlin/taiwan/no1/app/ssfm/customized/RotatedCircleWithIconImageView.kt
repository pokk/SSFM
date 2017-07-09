package taiwan.no1.app.ssfm.customized

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.*
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
            this.imageResource = R.drawable.sample_lady_gaga
        }
        this.addView(this.rotatedCircleImageView)
        this.controlButton = imageView(R.drawable.ic_play_arrow)
        this.timeControlButton = imageView()
        this.timeLabels = listOf(textView("00:00").apply {
            textColor = R.color.colorWhite
            backgroundColor = R.color.colorGreen
        }, textView("03:14"))
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

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        this.getChildAt(0).layout(
            this.width / 3 - this.getChildAt(0).measuredWidth / 2,
            this.height / 2 - this.getChildAt(0).measuredHeight / 2,
            this.width / 2 + this.getChildAt(0).measuredWidth / 2,
            this.height / 2 + this.getChildAt(0).measuredHeight / 2)
        this.getChildAt(1).layout(this.width / 2 - this.controlButton.measuredWidth / 2,
            this.height / 2 - this.controlButton.measuredHeight / 2,
            this.width / 2 + this.controlButton.measuredWidth / 2,
            this.height / 2 + this.controlButton.measuredHeight / 2)
        this.getChildAt(2).layout(this.width / 4 + this.width / 2,
            this.height - this.getChildAt(2).measuredHeight,
            this.width / 4 + this.width / 2 + this.getChildAt(2).measuredWidth,
            this.getChildAt(2).measuredHeight)
        this.getChildAt(3).layout(this.width / 4,
            this.height - this.getChildAt(3).measuredHeight,
            this.width / 4 + this.getChildAt(3).measuredWidth,
            this.getChildAt(3).measuredHeight)
    }
}
