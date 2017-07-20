package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import org.jetbrains.anko.backgroundColor

/**
 *
 * @author  jieyi
 * @since   7/17/17
 */
class CircularSeekBar: View {
    val unplayProgressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@CircularSeekBar.progressColor
            strokeCap = Paint.Cap.ROUND
            strokeWidth = this@CircularSeekBar.progressWidth
            style = Paint.Style.STROKE
        }
    }
    val playedProgressPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@CircularSeekBar.unprogressColor
            strokeCap = Paint.Cap.ROUND
            strokeWidth = this@CircularSeekBar.progressWidth
            style = Paint.Style.STROKE
        }
    }
    val controllerBtnPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = this@CircularSeekBar.btnColor
        }
    }
    val pm by lazy {
        PathMeasure().apply {
            setPath(Path().apply {
                addArc(RectF(0f, 0f, width.toFloat(), height.toFloat()), startAngle, endAngle)
            }, false)
        }
    }
    val rectF by lazy { RectF(0f, 0f, width.toFloat(), height.toFloat()) }

    var progressColor = R.color.colorRed
    var unprogressColor = R.color.colorWhite
    var progressWidth = 20f
    var startAngle = 140f
    var endAngle = 260f
    var btnColor = R.color.colorBlack
    var pressBtnColor = R.color.colorGray
    var btnRadius = 50f

    var rate = this.endAngle / 100f
        set(value) {
            field = this.endAngle / 100f
        }
    var isTouchButton = false
    var preX = 0f
    var preY = 0f
    var isVolumeUp = false
    var progress = 0
        set (value) {
            field = (value * this.rate).toInt()
        }
    var pos = floatArrayOf(0f, 0f)

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
            this.startAngle = it.getFloat(R.styleable.CircularSeekBar_start_degree, this.startAngle)
            this.endAngle = it.getFloat(R.styleable.CircularSeekBar_sweep_degree, this.endAngle)
            this.progressColor = it.getColor(R.styleable.CircularSeekBar_progress_color, this.progressColor)
            this.unprogressColor = it.getColor(R.styleable.CircularSeekBar_unprogress_color, this.unprogressColor)
            this.progressWidth = it.getFloat(R.styleable.CircularSeekBar_progress_width, this.progressWidth)
            this.progress = it.getInteger(R.styleable.CircularSeekBar_progress, this.progress)
            this.btnColor = it.getColor(R.styleable.CircularSeekBar_controller_color, this.btnColor)
            this.pressBtnColor = it.getColor(R.styleable.CircularSeekBar_unpress_controller_color,
                this.pressBtnColor)
            this.btnRadius = it.getFloat(R.styleable.CircularSeekBar_controller_radius, this.btnRadius)
        }.recycle()

        this.backgroundColor = R.color.colorChocolate
    }

    private fun calculateTouchDegree(posX: Float, posY: Float): Double {
        val x = posX - pivotX.toDouble()
        val y = posY - pivotY.toDouble()

        var angle = Math.toDegrees(Math.atan2(y, x) - this.startAngle / 180 * Math.PI)
        angle = (angle + 360) % 360

        return if (angle >= this.endAngle) 260.0 else angle
    }

    private fun calculateTouchProgress(angle: Double): Double = angle / this.endAngle * 100

    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                if (e.x in this.pos[0] - this.btnRadius..this.pos[0] + this.btnRadius &&
                    e.y in this.pos[1] - this.btnRadius..this.pos[1] + this.btnRadius)
                    this.isTouchButton = true
                this.controllerBtnPaint.color = R.color.colorCadetBlue
            }
            MotionEvent.ACTION_MOVE -> {
                if (!this.isTouchButton)
                    return true

                val down_degree = this.calculateTouchDegree(this.preX, this.preY)
                val degree = this.calculateTouchDegree(e.x, e.y)

                if (260.0 <= degree)
                    return true

                this.isVolumeUp = down_degree < degree
                this.progress = this.calculateTouchProgress(degree).toInt()

                this.invalidate()
            }
            MotionEvent.ACTION_UP -> {
                this.isTouchButton = false
            }
        }
        this.preX = e.x
        this.preY = e.y

        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        this.setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(this.rectF,
            this.startAngle + this.endAngle,
            -this.endAngle + this.progress,
            false,
            this.unplayProgressPaint)
        canvas.drawArc(this.rectF, this.startAngle, 0f + this.progress, false, this.playedProgressPaint)

        this.pm.getPosTan(this.pm.length / 100 * this.progress / this.rate, this.pos, null)

        canvas.drawCircle(this.pos[0], this.pos[1], this.btnRadius, this.controllerBtnPaint)
    }
}