package taiwan.no1.app.ssfm.misc.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.devrapid.kotlinknifer.getResColor
import com.devrapid.kotlinknifer.iff
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   7/17/17
 */
class CircularSeekBar: View {
    companion object {
        private const val MAX_VALUE = 100f
    }

    //region Variables for setting from the user
    var progress = .0
        set (value) {
            field = value * this.rate
            val rawValue = (field / this.rate).toInt()

            if (this@CircularSeekBar.isTouchButton) {
                this@CircularSeekBar.remainedTime = (this@CircularSeekBar.totalTime - rawValue * this@CircularSeekBar.totalTime / 100).toLong()
            }
            onProgressChanged?.let {
                if (rawValue != this@CircularSeekBar.tempProgress) {
                    it.invoke(rawValue, this@CircularSeekBar.remainedTime.toInt())
                }
            }
            if (MAX_VALUE.toInt() == rawValue)
                tempProgress = rawValue
            invalidate()
        }
    var progressColor = R.color.colorCoral
        set(value) {
            field = value
            this.playedProgressPaint.color = getResColor(field)
            postInv()
        }
    var unprogressColor = R.color.colorDarkGray
        set(value) {
            field = value
            this.unplayProgressPaint.color = getResColor(field)
            postInv()
        }
    var progressWidth = 13f
        set(value) {
            field = value
            this.playedProgressPaint.strokeWidth = field
            postInv()
        }
    var startDegree = 140f
        set(value) {
            field = value
            postInv()
        }
    var sweepDegree = 260f
        set(value) {
            field = value
            postInv()
        }
    var btnColor = R.color.colorBlue
        set(value) {
            field = value
            this.controllerBtnPaint.color = getResColor(field)
            postInv()
        }
    var pressBtnColor = R.color.colorDarkBlue
        set(value) {
            field = value
            this.controllerBtnPaint.color = getResColor(field)
            postInv()
        }
    var btnRadius = 25f
        set(value) {
            field = value
            postInv()
        }
    var totalTime = 0
    var onProgressChanged: ((progress: Int, remainedTime: Int) -> Unit)? = null
    var onProgressFinished: () -> Unit = {}
    var isTouchButton = false
        private set
    //endregion

    //region Private variables
    private val unplayProgressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getResColor(this@CircularSeekBar.progressColor)
            strokeCap = Paint.Cap.ROUND
            strokeWidth = this@CircularSeekBar.progressWidth
            style = Paint.Style.STROKE
        }
    }
    private val playedProgressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getResColor(this@CircularSeekBar.unprogressColor)
            strokeCap = Paint.Cap.ROUND
            strokeWidth = this@CircularSeekBar.progressWidth
            style = Paint.Style.STROKE
        }
    }
    private val controllerBtnPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getResColor(this@CircularSeekBar.btnColor)
        }
    }
    private val pm by lazy {
        PathMeasure().apply {
            setPath(Path().apply {
                addArc(RectF(0f + this@CircularSeekBar.paddingStart,
                    0f + this@CircularSeekBar.paddingTop,
                    width.toFloat() - this@CircularSeekBar.paddingEnd,
                    height.toFloat() - this@CircularSeekBar.paddingBottom),
                    this@CircularSeekBar.startDegree,
                    this@CircularSeekBar.sweepDegree)
            }, false)
        }
    }
    private val rectF by lazy {
        RectF(0f + this.paddingStart,
            0f + this.paddingTop,
            width.toFloat() - this.paddingEnd,
            height.toFloat() - this.paddingBottom)
    }
    private var rate = this.sweepDegree / MAX_VALUE
        set(value) {
            field = this.sweepDegree / MAX_VALUE
        }
    private var preX = 0f
    private var preY = 0f
    private var isVolumeUp = false
    private var pos = floatArrayOf(0f, 0f)
    private var isInit = true
    private var isAnimationRunning = false
    private var tempProgress = -1
    private var remainedTime = 0L
    private val postInv = { { this.invalidate() } iff this.isInit }
    private var animatorPlay = ValueAnimator.ofFloat(0f, MAX_VALUE)
    //endregion

    constructor(context: Context): super(context) {
        this.init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        this.init(context, attrs, defStyle)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.CircularSeekBar, defStyle, 0).also {
            this.startDegree = it.getFloat(R.styleable.CircularSeekBar_start_degree, this.startDegree)
            this.sweepDegree = it.getFloat(R.styleable.CircularSeekBar_sweep_degree, this.sweepDegree)
            this.progressColor = it.getColor(R.styleable.CircularSeekBar_progress_color, this.progressColor)
            this.unprogressColor = it.getColor(R.styleable.CircularSeekBar_unprogress_color, this.unprogressColor)
            this.progressWidth = it.getFloat(R.styleable.CircularSeekBar_progress_width, this.progressWidth)
            this.progress = it.getInteger(R.styleable.CircularSeekBar_progress, this.progress.toInt()).toDouble()
            this.btnColor = it.getColor(R.styleable.CircularSeekBar_controller_color, this.btnColor)
            this.pressBtnColor = it.getColor(R.styleable.CircularSeekBar_unpress_controller_color,
                this.pressBtnColor)
            this.btnRadius = it.getFloat(R.styleable.CircularSeekBar_controller_radius, this.btnRadius)
        }.recycle()

        this.isInit = true
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                if (e.x in this.pos[0] - this.btnRadius..this.pos[0] + this.btnRadius &&
                    e.y in this.pos[1] - this.btnRadius..this.pos[1] + this.btnRadius) {
                    this.isTouchButton = true
                    this.controllerBtnPaint.color = getResColor(this.pressBtnColor)

                    return true
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (!this.isTouchButton) {
                    return false
                }

                val down_degree = this.calculateTouchDegree(this.preX, this.preY)
                val degree = this.calculateTouchDegree(e.x, e.y)

                if (this.sweepDegree <= degree) {
                    return false
                }

                if (this.animatorPlay.isRunning) {
                    this.isAnimationRunning = true
                    this.stopAnimator()
                }

                this.isVolumeUp = down_degree < degree
                this.progress = this.calculateTouchProgress(degree)

                this.invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.isTouchButton = false
                this.controllerBtnPaint.color = getResColor(this.btnColor)
                if (this.isAnimationRunning) {
                    this.playAnimator(this@CircularSeekBar.remainedTime)
                    this.isAnimationRunning = false
                }
            }
        }
        this.preX = e.x
        this.preY = e.y

        return false
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(this.rectF,
            this.startDegree + this.sweepDegree,
            (-this.sweepDegree + this.progress).toFloat(),
            false,
            this.unplayProgressPaint)
        canvas.drawArc(this.rectF, this.startDegree, (0f + this.progress).toFloat(), false, this.playedProgressPaint)

        this.pm.getPosTan((this.pm.length / 100 * this.progress / this.rate).toFloat(), this.pos, null)

        canvas.drawCircle(this.pos[0], this.pos[1], this.btnRadius, this.controllerBtnPaint)
    }

    override fun onDetachedFromWindow() {
        this.onProgressChanged = null

        super.onDetachedFromWindow()
    }

    fun playAnimator(secondDuration: Long) {
        this.animatorPlay = ValueAnimator.ofFloat(
            (this@CircularSeekBar.progress / this@CircularSeekBar.rate).toFloat(), MAX_VALUE).apply {
            duration = secondDuration * 1000
            interpolator = LinearInterpolator()
            addUpdateListener {
                val value = it.animatedValue as Float
                this@CircularSeekBar.remainedTime = secondDuration - it.currentPlayTime / 1000
                this@CircularSeekBar.progress = value.toDouble()
            }
            start()
        }
    }

    fun stopAnimator() = this.animatorPlay.cancel()

    private fun calculateTouchDegree(posX: Float, posY: Float): Double {
        val x = posX - pivotX.toDouble()
        val y = posY - pivotY.toDouble()

        var angle = Math.toDegrees(Math.atan2(y, x) - this.startDegree / 180 * Math.PI)
        angle = (angle + 360) % 360

        return if (angle >= this.sweepDegree) this.sweepDegree.toDouble() else angle
    }

    private fun calculateTouchProgress(angle: Double): Double = angle / this.sweepDegree * 100
}