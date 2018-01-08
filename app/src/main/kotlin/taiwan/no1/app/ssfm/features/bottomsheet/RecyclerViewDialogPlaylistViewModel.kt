package taiwan.no1.app.ssfm.features.bottomsheet

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.loge
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_DISMISS_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.extension.createDebounce
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase

/**
 * @author  jieyi
 * @since   12/29/17
 */
class RecyclerViewDialogPlaylistViewModel(private val entity: BaseEntity,
                                          private val musicEntity: BaseEntity,
                                          private val addPlaylistItemCase: AddPlaylistItemCase) : BaseViewModel() {
    val playlistName by lazy { ObservableField<String>() }
    val playlistTrackNumber by lazy { ObservableField<String>() }
    /**
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.dismissPlaylistDialog]
     */
    private val debounceAddToPlaylist by lazy {
        createDebounce<View> {
            // OPTIMIZE(jieyi): 1/6/18 There might be some miss data type.
            val playlistId = (entity as PlaylistEntity).id
            val playlistParam = when (musicEntity) {
                is MusicEntity.Music -> {
                    PlaylistItemEntity(playlistId = playlistId,
                                       trackUri = musicEntity.url,
                                       trackName = musicEntity.title,
                                       artistName = musicEntity.artist,
                                       coverUrl = musicEntity.coverURL,
                                       lyricUrl = musicEntity.lyricURL,
                                       duration = musicEntity.length)
                }
                is MusicRankEntity.Song -> {
                    PlaylistItemEntity(playlistId = playlistId,
                                       trackUri = musicEntity.url,
                                       trackName = musicEntity.title,
                                       artistName = musicEntity.artist,
                                       coverUrl = musicEntity.coverURL,
                                       lyricUrl = musicEntity.lyricURL,
                                       duration = musicEntity.length)
                }
                is TrackEntity.BaseTrack -> {
                    PlaylistItemEntity(playlistId = playlistId,
                                       trackName = musicEntity.name.orEmpty(),
                                       artistName = musicEntity.artist?.name.orEmpty(),
                                       coverUrl = musicEntity.images?.get(ImageSizes.LARGE)?.text.orEmpty(),
                                       duration = musicEntity.duration?.toInt() ?: 0,
                                       isOffline = true)
                }
                else -> {
                    loge("The data type doesn't belong above.")
                    RxBus.get().post(VIEWMODEL_DISMISS_PLAYLIST_FRAGMENT_DIALOG, "")
                    null
                }
            }
            playlistParam?.let {
                lifecycleProvider.execute(addPlaylistItemCase, AddPlaylistItemUsecase.RequestValue(it)) {
                    onComplete { RxBus.get().post(VIEWMODEL_DISMISS_PLAYLIST_FRAGMENT_DIALOG, "") }
                }
            }
        }
    }

    init {
        (entity as PlaylistEntity).let {
            val pluralOfTrack = if (it.trackQuantity > 1) "s" else ""

            playlistName.set(it.name)
            playlistTrackNumber.set("- ${it.trackQuantity} track$pluralOfTrack")
        }
    }

    fun playlistOnClick(view: View) {
        debounceAddToPlaylist.onNext(view)
    }
}