package taiwan.no1.app.ssfm.features.search

import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.devrapid.kotlinknifer.formatToMoneyKarma
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_FOG_COLOR
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_IMAGE_URL
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchArtistChartViewModel(private val artist: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val playCount by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    var clickItemListener: ((item: ArtistEntity.Artist) -> Unit)? = null
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
        (artist as ArtistEntity.Artist).let {
            val count = (it.playCount?.toInt() ?: 0) / 1000

            artistName.set(it.name)
            playCount.set("${count.toString().formatToMoneyKarma()}K")
            thumbnail.set(it.images?.get(EXTRA_LARGE)?.text.orEmpty())
        }
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchViewModel.receiveClickHistoryEvent]
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
        clickItemListener?.invoke(artist)
    }
}