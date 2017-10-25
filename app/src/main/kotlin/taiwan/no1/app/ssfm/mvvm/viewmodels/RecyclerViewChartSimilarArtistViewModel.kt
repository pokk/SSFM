package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartSimilarArtistViewModel(val item: ArtistEntity.Artist): BaseViewModel() {
    val artistName by lazy { ObservableField<String>(item.name) }
    val thumbnail by lazy { ObservableField<String>(item.images?.get(LARGE)?.text ?: "") }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    var clickItemListener: ((item: ArtistEntity.Artist) -> Unit)? = null

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun artistOnClick(view: View) {
        // For `searching activity`.
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, item.name)
        // For `top chart activity`.
        clickItemListener?.invoke(item)
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