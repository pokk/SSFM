package taiwan.no1.app.ssfm.misc.extension.imageview

import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * @author  jieyi
 * @since   10/13/17
 */
@BindingAdapter("android:src")
fun ImageView.setSrc(bitmap: Bitmap) = setImageBitmap(bitmap)

@BindingAdapter("android:src")
fun ImageView.setSrc(resId: Int) = setImageResource(resId)

@BindingAdapter("android:imageUrl", "android:placeHolder", "android:error")
fun ImageView.loadImage(url: String?, holderDrawable: Drawable?, errorDrawable: Drawable?) {
    if (!url.isNullOrBlank()) {
        Glide.with(context).load(url).apply(RequestOptions().apply {
            centerCrop()
            holderDrawable?.let { placeholder(it) }
            errorDrawable?.let { error(it) }
            priority(Priority.HIGH)
            diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        }).into(this)
    }
}