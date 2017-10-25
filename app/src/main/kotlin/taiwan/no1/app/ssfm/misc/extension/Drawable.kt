package taiwan.no1.app.ssfm.misc.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ScaleDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.graphics.Palette

/**
 * @author  jieyi
 * @since   10/21/17
 */
fun Context.scaledDrawable(@DrawableRes drawableId: Int, scaleWidth: Float, scaleHeight: Float): Drawable {
    val drawable = ContextCompat.getDrawable(this, drawableId).apply {
        bounds = Rect(0, 0, (intrinsicWidth * scaleWidth).toInt(), (intrinsicHeight * scaleHeight).toInt())
    }
    return ScaleDrawable(drawable, 0, scaleWidth, scaleHeight).drawable
}

fun Bitmap.palette() = Palette.from(this)
