package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   12/29/17
 */
class RecyclerViewDialogPlaylistViewModel(val entity: BaseEntity) : BaseViewModel() {
    val playlistName by lazy { ObservableField<String>() }
    val playlistTrackNumber by lazy { ObservableField<String>() }

    init {
        (entity as PlaylistEntity).let {
            playlistName.set(it.name)
            playlistTrackNumber.set(it.trackQuantity.toString())
        }
    }

    fun playlistOnClick(view: View) {
    }
}