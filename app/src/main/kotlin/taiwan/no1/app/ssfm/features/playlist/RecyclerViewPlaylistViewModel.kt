package taiwan.no1.app.ssfm.features.playlist

import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.hwangjr.rxbus.RxBus
import kotlinx.android.synthetic.main.item_playlist_type_1.view.iv_playlist_image
import kotlinx.android.synthetic.main.item_playlist_type_1.view.tv_playlist_name
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.glideListener
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
    val showBackground by lazy { ObservableField<Boolean>() }
    val glideCallback = glideListener<Bitmap> {
        onResourceReady = { _, _, _, _, _ ->
            showBackground.set(true)
            false
        }
    }

    init {
        (item as PlaylistEntity).let {
            val pluralOfAlbum = if (it.albumQuantity > 1) "s" else ""
            val pluralOfTrack = if (it.trackQuantity > 1) "s" else ""

            playlistName.set(it.name)
            playlistTrackNumber.set("${it.albumQuantity} album$pluralOfAlbum, ${it.trackQuantity} track$pluralOfTrack")
            playlistThumbnail.set(it.imageUri)
        }
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.playlist.PlaylistActivity.navigateToPlaylistDetail]
     */
    fun playlistOnClick(view: View) {
        (item as PlaylistEntity).let {
            val sharedElements =
                listOf(Pair(view.iv_playlist_image, "transition_image_${it.id}"),
                       Pair(view.tv_playlist_name, "transition_name_${it.id}"))

            sharedElements.forEach { it.first.transitionName = it.second }

            RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_PLAYLIST, Pair(it, sharedElements))
        }
    }
}