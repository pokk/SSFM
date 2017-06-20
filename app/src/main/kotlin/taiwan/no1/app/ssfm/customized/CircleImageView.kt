package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import com.devrapid.kotlinknifer.getColor
import taiwan.no1.app.ssfm.R


/**
 *
 * @author  jieyi
 * @since   6/19/17
 */
class CircleImageView: ImageView {
    companion object {
        const val DEFAULT_BORDER_WIDTH = 0f
        const val DEFAULT_DORDER_COLOR = R.color.colorWhite
        const val DEFAULT_SHADOW_RADIUS = 0f
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
            this.invalidate()
        }
    var borderColor = DEFAULT_DORDER_COLOR
        set(value) {
            this.invalidate()
        }
    var shadowColor = DEFAULT_SHADOW_COLOR
        set(value) {
            this.invalidate()
        }

    private val paintBorder = Paint().apply { this.isAntiAlias = true }
    private val paint = Paint().apply { this.isAntiAlias = true }
    private var mRadius = 0  // 圓形圖片的半徑
    private var mScale = 0f  // 圖片的縮放比例
    private val mBitmap by lazy { this.drawableToBitmap(this.drawable) }
    private val mMatrix by lazy { Matrix() }
    private var mBitmapShader = BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

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
            this.borderColor = it.getColor(R.styleable.CircleImageView_border_color, getColor(DEFAULT_DORDER_COLOR))
            this.shadowRadius = it.getFloat(R.styleable.CircleImageView_shadow_radius, DEFAULT_SHADOW_RADIUS)
            this.shadowColor = it.getColor(R.styleable.CircleImageView_shadow_color, getColor(DEFAULT_SHADOW_COLOR))
        }.recycle()
    }

    override fun getScaleType(): ScaleType = ScaleType.CENTER_CROP

    override fun setScaleType(scaleType: ScaleType) {
        if (ScaleType.CENTER_CROP != scaleType) {
            throw IllegalArgumentException("ScaleType $scaleType not supported. ScaleType.CENTER_CROP is used by default. So you don't need to use ScaleType.")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val size = minOf(this.measuredHeight, this.measuredWidth)

        this.mRadius = size / 2
        this.setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        this.paint.shader = this.setBitmapShader()

        canvas.drawCircle(this.mRadius.toFloat(), this.mRadius.toFloat(), this.mRadius.toFloat(), this.paint)
    }

    private fun setBitmapShader() = BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP).also {
        it.setLocalMatrix(this.mMatrix.also {
            this.mScale = (mRadius * 2.0f) / minOf(this.mBitmap.height, this.mBitmap.width)
            it.setScale(this.mScale, this.mScale)
        })
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
