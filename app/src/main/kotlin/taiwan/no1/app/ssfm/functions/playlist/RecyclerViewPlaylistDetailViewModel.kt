package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   11/14/17
 */
class RecyclerViewPlaylistDetailViewModel(val item: BaseEntity) : BaseViewModel() {
    val index by lazy { ObservableField<String>() }
    val albumName by lazy { ObservableField<String>() }
    val trackName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val showBackground by lazy { ObservableField<Boolean>() }
    val glideCallback = glideListener<Bitmap> {
        onResourceReady = { _, _, _, _, _ ->
            showBackground.set(true)
            false
        }
    }

    init {
        (item as PlaylistItemEntity).let {
        }
    }

    fun trackOnClick(view: View) {

    }
}