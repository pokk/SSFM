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
import com.devrapid.kotlinknifer.getColorWithAlpha
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartSimilarArtistViewModel(val item: BaseEntity): BaseViewModel() {
    val artistName by lazy { ObservableField<String>((item as ArtistEntity.Artist).name) }
    val thumbnail by lazy { ObservableField<String>((item as ArtistEntity.Artist).images?.get(LARGE)?.text ?: "") }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    var clickItemListener: ((item: ArtistEntity.Artist) -> Unit)? = null

    /**
     * A callback event for clicking an artist to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.views.activities.ChartActivity.navigateToArtistDetail]
     */
    fun artistOnClick(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_SIMILAR, (item as ArtistEntity.Artist).name)
    }

    val imageCallback = object: RequestListener<Bitmap> {
        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean) =
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