package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewUniversal3ViewModel(val item: BaseEntity) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette().maximumColorCount(24).generate().let {
                textBackground.set(it.vibrantSwatch?.rgb ?: Color.BLACK)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
            }
            false
        }
    }

    init {
        (item as TrackEntity.Track).let {
            artistName.set(it.name)
            thumbnail.set(item.images?.get(LARGE)?.text.orEmpty())
        }
    }

    fun itemOnClick(view: View) {
    }
}