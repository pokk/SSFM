package taiwan.no1.app.ssfm.features.search

import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.devrapid.kotlinknifer.formatToMoneyKarma
import com.devrapid.kotlinknifer.glideListener
import com.devrapid.kotlinknifer.palette
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.features.chart.ChartArtistDetailFragment
import taiwan.no1.app.ssfm.misc.constants.Constant.RXBUS_PARAMETER_FRAGMENT
import taiwan.no1.app.ssfm.misc.constants.Constant.RXBUS_PARAMETER_FRAGMENT_NEEDBACK
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_FOG_COLOR
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_IMAGE_URL
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.NAVIGATION_TO_FRAGMENT
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchArtistChartViewModel(private var artist: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val playCount by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                color = gAlphaIntColor(it.darkVibrantSwatch?.rgb ?: gColor(R.color.colorPrimaryDark), 0.65f)
                false
            }
        }
    }
    private var color by Delegates.notNull<Int>()

    init {
        refreshView()
    }

    fun setArtistItem(item: BaseEntity) {
        this.artist = item
        refreshView()
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchViewModel.receiveClickHistoryEvent]
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.navigate]
     */
    fun artistOnClick(view: View) {
        val (keyword, imageUrl) = (artist as ArtistEntity.Artist).let {
            val k = it.name.orEmpty()
            val u = it.images?.get(EXTRA_LARGE)?.text.orEmpty()
            k to u
        }

        // For `searching activity`.
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY,
                         hashMapOf(VIEWMODEL_PARAMS_KEYWORD to keyword,
                                   VIEWMODEL_PARAMS_IMAGE_URL to imageUrl,
                                   VIEWMODEL_PARAMS_FOG_COLOR to color.toString()))
        // For `top chart activity`.
        (artist as ArtistEntity.Artist).let {
            RxBus.get().post(NAVIGATION_TO_FRAGMENT,
                             hashMapOf(RXBUS_PARAMETER_FRAGMENT to ChartArtistDetailFragment.newInstance(it.mbid.orEmpty(),
                                                                                                         it.name.orEmpty()),
                                       RXBUS_PARAMETER_FRAGMENT_NEEDBACK to true))
        }
    }

    private fun refreshView() {
        (artist as ArtistEntity.Artist).let {
            val count = (it.playCount?.toInt() ?: 0) / 1000

            artistName.set(it.name)
            playCount.set("${count.toString().formatToMoneyKarma()}K")
            thumbnail.set(it.images?.get(EXTRA_LARGE)?.text.orEmpty())
        }
    }
}
