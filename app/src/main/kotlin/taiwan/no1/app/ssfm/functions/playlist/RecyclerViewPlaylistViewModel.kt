package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewPlaylistViewModel(val item: BaseEntity): BaseViewModel() {
    val playlistName by lazy { ObservableField<String>() }
    val playlistTrackNumber by lazy { ObservableField<String>() }
    val playlistThumbnail by lazy { ObservableField<String>() }

    init {
        (item as PlaylistEntity).let {
            val pluralOfAlbum = if (it.album_quantity > 1) "s" else ""
            val pluralOfTrack = if (it.track_quantity > 1) "s" else ""

            playlistName.set(it.name)
            playlistTrackNumber.set("${it.album_quantity} album$pluralOfAlbum, ${it.track_quantity} track$pluralOfTrack")
            playlistThumbnail.set(it.image_uri)
        }
    }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, (item as TrackEntity.Track).name)
    }
}