package taiwan.no1.app.ssfm.misc.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View

/**
 * @author  jieyi
 * @since   11/6/17
 */
class BlurryImageView @JvmOverloads constructor(context: Context,
                                                attrs: AttributeSet? = null,
                                                defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private var bitmap: Bitmap? = null
    private val paint by lazy { Paint() }

    fun inputBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap

        val shaderA = LinearGradient(0f,
                                     0f,
                                     0f,
                                     bitmap.height.toFloat(),
                                     -0x1,
                                     0x00ffffff,
                                     Shader.TileMode.CLAMP)
        val shaderB = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = ComposeShader(shaderA, shaderB, PorterDuff.Mode.SRC_IN)

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f,
                        0f,
                        bitmap?.width?.toFloat() ?: 0f,
                        bitmap?.height?.toFloat() ?: 0f,
                        paint)
    }
}