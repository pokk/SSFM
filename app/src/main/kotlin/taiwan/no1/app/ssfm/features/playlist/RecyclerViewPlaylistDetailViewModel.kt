package taiwan.no1.app.ssfm.features.playlist

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.features.base.BaseViewModel
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
    val artistName by lazy { ObservableField<String>() }
    val trackName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val showBackground by lazy { ObservableBoolean() }
    val isPlaying by lazy { ObservableBoolean() }
    val glideCallback = glideListener<Bitmap> {
        onResourceReady = { _, _, _, _, _ ->
            showBackground.set(true)
            false
        }
    }

    init {
        (item as PlaylistItemEntity).let {
            artistName.set(it.artistName)
            trackName.set(it.trackName)
            duration.set(it.duration.toTimeString())
            thumbnail.set(it.coverUrl)
        }
    }

    fun trackOnClick(view: View) {

    }
}