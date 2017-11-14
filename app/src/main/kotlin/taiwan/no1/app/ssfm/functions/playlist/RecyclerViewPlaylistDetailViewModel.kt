package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableField
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
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

    init {
        (item as PlaylistItemEntity).let {
        }
    }
}