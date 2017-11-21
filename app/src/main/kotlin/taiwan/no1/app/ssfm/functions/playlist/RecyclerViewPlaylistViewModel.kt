package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.item_playlist_type_1.view.iv_playlist_image
import kotlinx.android.synthetic.main.item_playlist_type_1.view.tv_playlist_name
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewPlaylistViewModel(val item: BaseEntity) : BaseViewModel() {
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
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.playlist.PlaylistActivity.navigateToPlaylistDetail]
     */
    fun playlistOnClick(view: View) {
        item as PlaylistEntity
        val sharedElements =
            listOf(Pair(view.iv_playlist_image, "transition_image_${item.id}"),
                Pair(view.tv_playlist_name, "transition_name_${item.id}"))

        sharedElements.forEach { it.first.transitionName = it.second }

        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_PLAYLIST, Pair(item, sharedElements))
    }
}