package taiwan.no1.app.ssfm.functions.search

import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_FOG_COLOR
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_IMAGE_URL
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchTrackChartViewModel(val track: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val layoutBackground by lazy { ObservableField<Drawable>() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette().maximumColorCount(24).generate().let { palette ->
                val start = gAlphaIntColor(palette.vibrantSwatch?.rgb ?:
                    gColor(R.color.colorSimilarPrimaryDark), 0.65f)
                darkColor = gAlphaIntColor(palette.darkVibrantSwatch?.rgb ?:
                    gColor(R.color.colorPrimaryDark), 0.65f)
                val background = GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(start, darkColor))

                layoutBackground.set(background)
            }
            false
        }
    }
    private var darkColor by Delegates.notNull<Int>()

    init {
        (track as TrackEntity.Track).let {
            trackName.set(it.name)
            artistName.set(it.artist?.name.orEmpty())
            thumbnail.set(it.images?.get(LARGE)?.text.orEmpty())
        }
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        val (keyword, imageUrl) = (track as TrackEntity.Track).let {
            val k = it.artist?.let { artist -> "${it.name} ${artist.name}" } ?: it.name.orEmpty()
            val u = it.images?.get(EXTRA_LARGE)?.text.orEmpty()
            k to u
        }

        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY,
            hashMapOf(VIEWMODEL_PARAMS_KEYWORD to keyword,
                VIEWMODEL_PARAMS_IMAGE_URL to imageUrl,
                VIEWMODEL_PARAMS_FOG_COLOR to darkColor.toString()))
    }
}