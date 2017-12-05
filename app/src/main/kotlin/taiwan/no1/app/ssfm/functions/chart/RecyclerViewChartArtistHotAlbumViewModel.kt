package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartArtistHotAlbumViewModel(val item: BaseEntity) : BaseViewModel() {
    val imageUrl by lazy { ObservableField<String>() }
    val albumName by lazy { ObservableField<String>() }
    val background by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    var clickItemListener: ((item: AlbumEntity.AlbumWithPlaycount) -> Unit)? = null

    init {
        (item as AlbumEntity.AlbumWithPlaycount).let {
            imageUrl.set(it.images?.get(EXTRA_LARGE)?.text.orEmpty())
            albumName.set(it.name.orEmpty())
        }
    }

    fun albumOnClick(view: View) {
        clickItemListener?.invoke(item as AlbumEntity.AlbumWithPlaycount)
    }

    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                gAlphaIntColor(it.mutedSwatch?.rgb ?: Color.BLACK, 1f).let(background::set)
                textColor.set(it.mutedSwatch?.titleTextColor ?: Color.LTGRAY)
            }
            false
        }
    }
}