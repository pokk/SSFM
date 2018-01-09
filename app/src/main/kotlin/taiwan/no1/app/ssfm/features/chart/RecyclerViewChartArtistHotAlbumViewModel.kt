package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.devrapid.kotlinknifer.glideListener
import com.devrapid.kotlinknifer.palette
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartArtistHotAlbumViewModel(private var item: BaseEntity) : BaseViewModel() {
    val imageUrl by lazy { ObservableField<String>() }
    val albumName by lazy { ObservableField<String>() }
    val background by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    // OPTIMIZE(jieyi): 1/6/18 Fix this place!!!!!!!!!!!!!!!!!
    var clickItemListener: ((item: AlbumEntity.AlbumWithPlaycount) -> Unit)? = null
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                gAlphaIntColor(it.mutedSwatch?.rgb ?: Color.BLACK, 1f).let(background::set)
                textColor.set(it.mutedSwatch?.titleTextColor ?: Color.LTGRAY)
            }
            false
        }
    }

    init {
        refreshView()
    }

    fun setAlbumItem(item: BaseEntity) {
        this.item = item
        refreshView()
    }

    fun albumOnClick(view: View) {
        clickItemListener?.invoke(item as AlbumEntity.AlbumWithPlaycount)
    }

    private fun refreshView() {
        (item as AlbumEntity.AlbumWithPlaycount).let {
            imageUrl.set(it.images?.get(EXTRA_LARGE)?.text.orEmpty())
            albumName.set(it.name.orEmpty())
        }
    }
}