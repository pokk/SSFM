package taiwan.no1.app.ssfm.features.bottomsheet

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_DISSMISS_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.extension.createDebounce
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase

/**
 * @author  jieyi
 * @since   12/29/17
 */
class RecyclerViewDialogPlaylistViewModel(entity: BaseEntity,
                                          private val musicEntity: BaseEntity,
                                          private val addPlaylistItemCase: AddPlaylistItemCase) : BaseViewModel() {
    val playlistName by lazy { ObservableField<String>() }
    val playlistTrackNumber by lazy { ObservableField<String>() }
    /**
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.dismissPlaylistDialog]
     */
    private val debounceAddToPlaylist by lazy {
        createDebounce<View> {
            val playlistParam = when (musicEntity) {
                is MusicEntity.Music -> {
                    PlaylistItemEntity(trackUri = musicEntity.url,
                        trackName = musicEntity.title,
                        artistName = musicEntity.artist,
                        coverUrl = musicEntity.coverURL,
                        lyricUrl = musicEntity.lyricURL,
                        duration = musicEntity.length)
                }
                is MusicRankEntity.Song -> {
                    PlaylistItemEntity(trackUri = musicEntity.url,
                        trackName = musicEntity.title,
                        artistName = musicEntity.artist,
                        coverUrl = musicEntity.coverURL,
                        lyricUrl = musicEntity.lyricURL,
                        duration = musicEntity.length)
                }
                else -> null
            }
            playlistParam?.let {
                lifecycleProvider.execute(addPlaylistItemCase, AddPlaylistItemUsecase.RequestValue(it)) {
                    onNext { logw() }
                    onComplete { RxBus.get().post(VIEWMODEL_DISSMISS_PLAYLIST_FRAGMENT_DIALOG, "") }
                }
            }
        }
    }

    init {
        (entity as PlaylistEntity).let {
            playlistName.set(it.name)
            playlistTrackNumber.set(it.trackQuantity.toString())
        }
    }

    fun playlistOnClick(view: View) = debounceAddToPlaylist.onNext(view)
}