package taiwan.no1.app.ssfm.functions.search

import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchTrackChartViewModel(val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val layoutBackground by lazy { ObservableField<Drawable>() }
    val imageCallback = object : RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?,
                                  model: Any?,
                                  target: Target<Bitmap>?,
                                  isFirstResource: Boolean): Boolean = false

        override fun onResourceReady(resource: Bitmap,
                                     model: Any?,
                                     target: Target<Bitmap>?,
                                     dataSource: DataSource?,
                                     isFirstResource: Boolean): Boolean =
            resource.palette().maximumColorCount(24).generate().let { palette ->
                val start = gAlphaIntColor(palette.vibrantSwatch?.rgb ?:
                    gColor(R.color.colorSimilarPrimaryDark), 0.5f)
                val end = gAlphaIntColor(palette.darkVibrantSwatch?.rgb ?:
                    gColor(R.color.colorPrimaryDark), 0.5f)
                val background = GradientDrawable(GradientDrawable.Orientation.BR_TL, intArrayOf(end, start))

                layoutBackground.set(background)
                false
            }
    }

    init {
        (item as TrackEntity.Track).let {
            trackName.set(it.name)
            artistName.set(it.artist?.name ?: "")
            thumbnail.set(it.images?.get(LARGE)?.text ?: "")
        }
    }

    /**
     * A callback event for clicking a item to list item.
     *
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        item as TrackEntity.Track
        val keyword = item.artist?.let { "${it.name} ${item.name}" } ?: item.name

        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY, keyword)
    }
}