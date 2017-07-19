package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.devrapid.kotlinknifer.logw
import org.jetbrains.anko.backgroundColor

/**
 *
 * @author  jieyi
 * @since   7/17/17
 */
class CircularSeekBar: View {
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
//        context.obtainStyledAttributes(attrs, R.styleable.RotatedCircleWithIconImageView, defStyle, 0).also {
//        }.recycle()
        backgroundColor = R.color.colorChocolate
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec)
    }

    val p1 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = R.color.colorRed
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f
        style = Paint.Style.STROKE
    }
    val p2 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = R.color.colorWhite
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 20f
        style = Paint.Style.STROKE
    }

    val rectF by lazy { RectF(0f, 0f, width.toFloat(), height.toFloat()) }
    val start_angle = 140f
    val end_angle = 260f
    val rate = end_angle / 100f
    var progress = 0
        set (value) {
            field = (value * rate).toInt()
        }
    var temp_progress = 0
        set(value) {
            field = (value / rate).toInt()
        }
    var is_touch_btn = false

    var down_x = 0f
    var down_y = 0f

    override fun onTouchEvent(e: MotionEvent): Boolean {
        down_x = e.x
        down_y = e.y

        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                if (e.x in pos[0] - btn_radius..pos[0] + btn_radius && e.y in pos[1] - btn_radius..pos[1] + btn_radius)
                    is_touch_btn = true
                p3.color = R.color.colorCadetBlue
            }
            MotionEvent.ACTION_MOVE -> {
                if (!is_touch_btn)
                    return true

                val degree = calculateTouchDegree(e.x, e.y)
                progress = calculateTouchProgress(degree).toInt()
                logw(progress, degree)

                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                is_touch_btn = false
            }
        }

        return true
    }

    val p3 = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = R.color.colorBlack
    }
    val pm by lazy {
        PathMeasure().apply {
            setPath(Path().apply { addArc(RectF(0f, 0f, width.toFloat(), height.toFloat()), start_angle, end_angle) },
                false)
        }
    }
    var pos = floatArrayOf(0f, 0f)
    var btn_radius = 50f

    override fun onDraw(canvas: Canvas) {
        canvas.drawArc(rectF, start_angle + end_angle, -end_angle + progress, false, p1)
        canvas.drawArc(rectF, start_angle, 0f + progress, false, p2)

        pm.getPosTan(pm.length / 100 * progress / rate, pos, null)

        canvas.drawCircle(pos[0], pos[1], btn_radius, p3)
    }

    fun calculateTouchDegree(posX: Float, posY: Float): Double {
        val x = posX - pivotX.toDouble()
        val y = posY - pivotY.toDouble()

        var angle = Math.toDegrees(Math.atan2(y, x) - start_angle / 180 * Math.PI)
        angle = (angle + 360) % 360

        return if (angle >= end_angle) 260.0 else angle
    }

    fun calculateTouchProgress(angle: Double): Double = angle / end_angle * 100
}
