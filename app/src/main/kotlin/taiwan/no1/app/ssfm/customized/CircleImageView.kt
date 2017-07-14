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
import org.jetbrains.anko.imageBitmap
import taiwan.no1.app.ssfm.R


/**
 * Circle image view with shadow.
 *
 * @author  jieyi
 * @since   6/19/17
 */
open class CircleImageView: ImageView {
    companion object {
        private const val DEFAULT_BORDER_WIDTH = 0f
        private const val DEFAULT_BORDER_COLOR = R.color.colorWhite
        private const val DEFAULT_SHADOW_RADIUS = 0f
        private const val DEFAULT_SHADOW_COLOR = R.color.colorWhite
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

    protected val paintBorder = Paint().apply {
        this.isAntiAlias = true
    }
    protected val paintImg = Paint().apply { this.isAntiAlias = true }
    protected var mRadius = 0
    protected var mScale = 0f
    protected val mBitmap by lazy { this.drawableToBitmap(this.drawable) }
    protected val mMatrix by lazy { Matrix() }

    constructor(context: Context): super(context) {
        this.init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        this.init(context, attrs, defStyleAttr)
    }

    protected open fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0).also {
            this.borderWidth = it.getDimension(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH)
            this.borderColor = it.getColor(R.styleable.CircleImageView_border_color, getResColor(DEFAULT_BORDER_COLOR))
//            this.shadowRadius = it.getFloat(R.styleable.CircleImageView_shadow_radius, DEFAULT_SHADOW_RADIUS)
//            this.shadowColor = it.getColor(R.styleable.CircleImageView_shadow_color, getResColor(DEFAULT_SHADOW_COLOR))
        }.recycle()

        this.paintBorder.color = this.borderColor
    }

    override fun getScaleType(): ScaleType = ScaleType.FIT_XY

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
        this.imageBitmap = Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, size, size, false)
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

    override fun onStartTemporaryDetach() {
        super.onStartTemporaryDetach()

        this.setOnClickListener(null)
    }

    protected fun setBitmapShader() = BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).also {
        it.setLocalMatrix(this.mMatrix.also {
            mScale = mRadius.times(2).div(minOf(this.mBitmap.height, this.mBitmap.width)).toFloat()
            it.setScale(mScale, mScale)
        })
    }

    protected fun drawShadow() {
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, this.paintBorder)
        this.paintBorder.setShadowLayer(this.shadowRadius.times(1.5f), 0f, this.shadowRadius.div(2), this.shadowColor)
    }

    protected fun drawableToBitmap(drawable: Drawable): Bitmap {
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
