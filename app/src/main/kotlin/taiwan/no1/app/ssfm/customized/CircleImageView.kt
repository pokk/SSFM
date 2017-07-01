package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.devrapid.kotlinknifer.getResColor
import taiwan.no1.app.ssfm.R


/**
 * Circle image view with shadow.
 *
 * @author  jieyi
 * @since   6/19/17
 */
class CircleImageView: ImageView {
    companion object {
        const val DEFAULT_BORDER_WIDTH = 0f
        const val DEFAULT_BORDER_COLOR = R.color.colorWhite
        const val DEFAULT_SHADOW_RADIUS = 8f
        const val DEFAULT_SHADOW_COLOR = R.color.colorWhite
    }

    var borderWidth = DEFAULT_BORDER_WIDTH
        set(value) {
            field = value
            this.requestLayout()
            this.invalidate()
        }
    var shadowRadius = DEFAULT_SHADOW_RADIUS
        set(value) {
            field = value
            drawShadow()
            this.invalidate()
        }
    var borderColor = getResColor(DEFAULT_BORDER_COLOR)
        set(@ColorInt value) {
            field = value
            this.paintBorder.color = value
            this.invalidate()
        }
    var shadowColor = getResColor(DEFAULT_SHADOW_COLOR)
        set(@ColorInt value) {
            field = value
            drawShadow()
            this.invalidate()
        }

    private val paintBorder = Paint().apply {
        this.isAntiAlias = true
    }
    private val paintImg = Paint().apply { this.isAntiAlias = true }
    private var mRadius = 0
    private var mScale = 0f
    private val mBitmap by lazy { this.drawableToBitmap(this.drawable) }
    private val mMatrix by lazy { Matrix() }

    constructor(context: Context): super(context) {
        this.init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0).also {
            this.borderWidth = it.getDimension(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH)
            this.borderColor = it.getColor(R.styleable.CircleImageView_border_color, getResColor(DEFAULT_BORDER_COLOR))
            this.shadowRadius = it.getFloat(R.styleable.CircleImageView_shadow_radius, DEFAULT_SHADOW_RADIUS)
            this.shadowColor = it.getColor(R.styleable.CircleImageView_shadow_color, getResColor(DEFAULT_SHADOW_COLOR))
        }.recycle()

        this.paintBorder.color = this.borderColor
    }

    override fun getScaleType(): ScaleType = ScaleType.CENTER_CROP

    override fun setScaleType(scaleType: ScaleType) {
        if (ScaleType.CENTER_CROP != scaleType) {
            error("ScaleType $scaleType not supported. ScaleType.CENTER_CROP is used by default. So you don't need to use ScaleType.")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = minOf(this.measuredHeight, this.measuredWidth)

        this.mRadius = size.div(2)
        this.setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        this.paintImg.shader = this.setBitmapShader()

        // Draw the circle border.
        canvas.drawCircle(this.mRadius.toFloat(),
            this.mRadius.toFloat(),
            this.mRadius.minus(this.shadowRadius.plus(this.shadowRadius.div(2))),
            this.paintBorder)
        // Draw the circle image.
        canvas.drawCircle(this.mRadius.toFloat(),
            this.mRadius.toFloat(),
            this.mRadius.minus(this.borderWidth).minus(this.shadowRadius.plus(this.shadowRadius.div(2))),
            this.paintImg)
    }

    private fun setBitmapShader() = BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).also {
        it.setLocalMatrix(this.mMatrix.also {
            this.mScale = mRadius.times(2).div(minOf(this.mBitmap.height, this.mBitmap.width)).toFloat()
            it.setScale(this.mScale, this.mScale)
        })
    }

    private fun drawShadow() {
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, this.paintBorder)
        this.paintBorder.setShadowLayer(this.shadowRadius.times(1.5f), 0f, this.shadowRadius.div(2), this.shadowColor)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        return drawable.let {
            val (width, height) = Pair(it.intrinsicWidth, it.intrinsicHeight)

            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                it.setBounds(0, 0, width, height)
                it.draw(Canvas(this))
            }
        }
    }
}
