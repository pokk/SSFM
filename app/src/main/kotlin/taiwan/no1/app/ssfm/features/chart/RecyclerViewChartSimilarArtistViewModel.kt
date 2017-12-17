package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartSimilarArtistViewModel(val item: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    var clickItemListener: ((item: ArtistEntity.Artist) -> Unit)? = null

    init {
        (item as ArtistEntity.Artist).let {
            artistName.set(it.name)
            thumbnail.set(it.images?.get(LARGE)?.text.orEmpty())
        }
    }

    /**
     * A callback event for clicking an artist to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.navigateToArtistDetail]
     */
    fun artistOnClick(view: View) {
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_SIMILAR, (item as ArtistEntity.Artist).name)
    }

    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                gAlphaIntColor(it.vibrantSwatch?.rgb ?: Color.BLACK, 0.5f).let(textBackground::set)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
            }
            false
        }
    }
}