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

    // (x1, y1) is an old position, (x2, y2) is a new position.
    val distance: (Pair<Double, Double>, Pair<Double, Double>) -> Double = { (x1, y1), (x2, y2) ->
        // Third quadrant.
        val isPlus = if (pivotX > x1 && pivotY < y1)
            if (x1 > x2 || y1 > y2) 1 else -1
        // Second quadrant.
        else if (pivotX > x1 && pivotY > y1)
            if (x1 > x2 || y1 > y2) 1 else -1

        // First quadrant.
        else if (pivotX < x1 && pivotY > y1)
            if (x1 > x2 || y1 > y2) 1 else -1
        // Fourth quadrant.
        else
            if (x1 > x2 || y1 > y2) 1 else -1

        Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0)) *
    }

    val pm2
        get() = PathMeasure().apply {
            setPath(Path().apply { addArc(rectF, start_angle, 0f + progress) }, false)
        }

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

                if (!(e.x in pos[0] - btn_radius..pos[0] + btn_radius && e.y in pos[1] - btn_radius..pos[1] + btn_radius)) {
                    is_touch_btn = false

                    return true
                }

                val d = distance(Pair(down_x.toDouble(), down_y.toDouble()), Pair(e.x.toDouble(), e.y.toDouble()))
                progress = temp_progress + (d / 10f).toInt()
                if (temp_progress + (d / 10f).toInt() >= 100)
                    progress = 100
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                temp_progress = progress
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

        var percents = (pm2.length / pm.length) * 100
        progress = percents.toInt()

        canvas.drawCircle(pos[0], pos[1], btn_radius, p3)
    }
}