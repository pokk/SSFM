package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewUniversal3ViewModel(val item: BaseEntity): BaseViewModel() {
    val artistName by lazy { ObservableField<String>((item as TrackEntity.Track).name) }
    val thumbnail by lazy { ObservableField<String>((item as TrackEntity.Track).images?.get(ImageSizes.LARGE)?.text ?: "") }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }

    fun itemOnClick(view: View) {
    }

    val imageCallback = object: RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean) =
            false

        override fun onResourceReady(resource: Bitmap,
                                     model: Any?,
                                     target: Target<Bitmap>?,
                                     dataSource: DataSource?,
                                     isFirstResource: Boolean) =
            resource.palette().
                maximumColorCount(24).
                generate().let {
                textBackground.set(it.vibrantSwatch?.rgb ?: Color.BLACK)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
                false
            }
    }
}