package taiwan.no1.app.ssfm.functions.playlist

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistCase

/**
 * @author  jieyi
 * @since   11/10/17
 */
class PlaylistIndexFragmentViewModel(private val getPlaylistsUsecase: FetchPlaylistCase,
                                     private val getPlaylistItemsUsecase: FetchPlaylistItemCase,
                                     private val removePlaylistUsecase: RemovePlaylistCase) : BaseViewModel() {
    fun fetchPlaylistAndRecently(playlistCallback: (List<PlaylistEntity>) -> Unit,
                                 recentlyCallback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistsUsecase) {
            onNext {
                // All playlist without the history `index 0` playlist.
                it.toMutableList().apply { removeAt(0) }.let(playlistCallback)
            }
            onComplete {
                lifecycleProvider.execute(getPlaylistItemsUsecase,
                    GetPlaylistItemsUsecase.RequestValue(DATABASE_PLAYLIST_HISTORY_ID.toLong())) {
                    onNext(recentlyCallback)
                }
            }
        }
    }

    fun deletePlaylist(playlist: PlaylistEntity, callback: (Boolean) -> Unit) {
        lifecycleProvider.execute(removePlaylistUsecase, AddPlaylistUsecase.RequestValue(playlist)) {
            onNext(callback)
        }
    }
}