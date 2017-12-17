package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_NAME
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
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
                gAlphaIntColor(it.vibrantSwatch?.rgb ?: Color.BLACK, 0.5f).let(textBackground::set)
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
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.navigateToAlbumDetail]
     */
    fun itemOnClick(view: View) {
        item as AlbumEntity.AlbumWithArtist
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_ALBUM,
            hashMapOf(VIEWMODEL_PARAMS_ARTIST_NAME to item.artist?.name,
                VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME to item.name))
    }
}