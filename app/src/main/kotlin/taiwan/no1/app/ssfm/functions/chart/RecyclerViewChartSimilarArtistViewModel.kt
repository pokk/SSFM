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
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
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
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.chart.ChartActivity.navigateToArtistDetail]
     */
    fun artistOnClick(view: View) {
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_SIMILAR, (item as ArtistEntity.Artist).name)
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
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
                false
            }
    }
}