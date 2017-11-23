package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.gAlphaColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewUniversal1ViewModel(val item: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    val shadowColor by lazy { ObservableInt() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                gAlphaColor(it.vibrantSwatch?.rgb ?: Color.BLACK, 0.5f).let(textBackground::set)
                shadowColor.set(it.vibrantSwatch?.rgb ?: Color.BLACK)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
            }
            false
        }
    }

    init {
        (item as AlbumEntity.AlbumWithArtist).let {
            artistName.set(it.name)
            thumbnail.set(item.images?.get(LARGE)?.text.orEmpty())
        }
    }

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
}