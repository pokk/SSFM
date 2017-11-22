package taiwan.no1.app.ssfm.misc.extension.imageview

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.mikhaellopez.circularimageview.CircularImageView

/**
 * @author  jieyi
 * @since   10/13/17
 */
@BindingAdapter("android:src")
fun ImageView.setSrc(bitmap: Bitmap) = setImageBitmap(bitmap)

@BindingAdapter("android:src")
fun ImageView.setSrc(resId: Int) = setImageResource(resId)

@BindingAdapter("android:bkgDrawable")
fun ImageView.setBackgroundDrawable(drawable: Drawable?) {
    drawable?.let { background = it }
}

@BindingAdapter("android:imageUrl",
    "android:placeHolder",
    "android:error",
    "android:imgCallback",
    requireAll = false)
fun ImageView.loadImage(url: String?,
                        holderDrawable: Drawable?,
                        errorDrawable: Drawable?,
                        listener: RequestListener<Bitmap>?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context).asBitmap().load(url).apply(RequestOptions().apply {
            centerCrop()
            holderDrawable?.let(this::placeholder)
            errorDrawable?.let(this::error)
            priority(Priority.HIGH)
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }).apply {
            listener?.let(this::listener)
        }.into(this)
    }
}

@BindingAdapter("android:shadowColor")
fun CircularImageView.addShadowColor(color: Int) = setShadowColor(color)
