package taiwan.no1.app.ssfm.customized

import android.content.Context

import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.devrapid.kotlinknifer.getResColor
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   7/17/17
 */
class CircularSeekBar: View {
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
    private var rate = this.sweepDegree / 100f
        set(value) {
            field = this.sweepDegree / 100f
        }
    private var isTouchButton = false
    private var preX = 0f
    private var preY = 0f
    private var isVolumeUp = false
    private var progress = 0
        set (value) {
            field = (value * this.rate).toInt()
        }
    private var pos = floatArrayOf(0f, 0f)
    private var isInit = true
    private val postInv = {
        if (this.isInit)
            this.invalidate()
    }

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
            this.progress = it.getInteger(R.styleable.CircularSeekBar_progress, this.progress)
            this.btnColor = it.getColor(R.styleable.CircularSeekBar_controller_color, this.btnColor)
            this.pressBtnColor = it.getColor(R.styleable.CircularSeekBar_unpress_controller_color,
                this.pressBtnColor)
            this.btnRadius = it.getFloat(R.styleable.CircularSeekBar_controller_radius, this.btnRadius)
        }.recycle()

        this.isInit = true
    }

    private fun calculateTouchDegree(posX: Float, posY: Float): Double {
        val x = posX - pivotX.toDouble()
        val y = posY - pivotY.toDouble()

        var angle = Math.toDegrees(Math.atan2(y, x) - this.startDegree / 180 * Math.PI)
        angle = (angle + 360) % 360

        return if (angle >= this.sweepDegree) this.sweepDegree.toDouble() else angle
    }

    private fun calculateTouchProgress(angle: Double): Double = angle / this.sweepDegree * 100

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

                this.isVolumeUp = down_degree < degree
                this.progress = this.calculateTouchProgress(degree).toInt()

                this.invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                this.isTouchButton = false
                this.controllerBtnPaint.color = getResColor(this.btnColor)
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
            -this.sweepDegree + this.progress,
            false,
            this.unplayProgressPaint)
        canvas.drawArc(this.rectF, this.startDegree, 0f + this.progress, false, this.playedProgressPaint)

        this.pm.getPosTan(this.pm.length / 100 * this.progress / this.rate, this.pos, null)

        canvas.drawCircle(this.pos[0], this.pos[1], this.btnRadius, this.controllerBtnPaint)
    }
}