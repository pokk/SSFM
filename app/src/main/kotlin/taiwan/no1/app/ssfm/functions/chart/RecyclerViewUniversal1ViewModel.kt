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
import com.devrapid.kotlinknifer.getColorWithAlpha
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewUniversal1ViewModel(val item: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>((item as AlbumEntity.AlbumWithArtist).name) }
    val thumbnail by lazy {
        ObservableField<String>((item as AlbumEntity.AlbumWithArtist).images?.get(ImageSizes.LARGE)?.text.orEmpty())
    }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    val shadowColor by lazy { ObservableInt() }

    /**
     * A callback event for clicking an artist to list item.
     *
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.chart.ChartActivity.navigateToAlbumDetail]
     */
    fun itemOnClick(view: View) {
        item as AlbumEntity.AlbumWithArtist
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_ALBUM,
            hashMapOf("Artist Name" to item.artist?.name, "Artist Album Name" to item.name))
    }

    val imageCallback = object : RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?,
                                  model: Any?,
                                  target: Target<Bitmap>?,
                                  isFirstResource: Boolean) =
            false

        override fun onResourceReady(resource: Bitmap,
                                     model: Any?,
                                     target: Target<Bitmap>?,
                                     dataSource: DataSource?,
                                     isFirstResource: Boolean) =
            resource.palette().maximumColorCount(24).generate().let {
                App.appComponent.context().
                    getColorWithAlpha(it.vibrantSwatch?.rgb ?: Color.BLACK, 0.5f).
                    let(textBackground::set)
                shadowColor.set(it.vibrantSwatch?.rgb ?: Color.BLACK)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
                false
            }
    }
}