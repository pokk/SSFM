package taiwan.no1.app.ssfm.features.playlist

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.createDebounce
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
    val artistName by lazy { ObservableField<String>() }
    private val debounceTrackClick by lazy {
        createDebounce<View> {
        }
    }

    init {
        (item as PlaylistItemEntity).let {
            trackName.set(it.trackName)
            artistName.set(it.artistName)
            trackDuration.set(it.duration.toTimeString())
        }
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) = debounceTrackClick.onNext(view)
}