package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewRecentlyPlaylistViewModel(val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }
    val albumName by lazy { ObservableField<String>() }

    init {
        (item as PlaylistItemEntity).let {
            trackName.set(it.track_name)
            albumName.set(it.album_name)
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
    }
}