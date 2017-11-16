package taiwan.no1.app.ssfm.functions.playlist

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * @author  jieyi
 * @since   11/10/17
 */
class PlaylistIndexFragmentViewModel(private val getPlaylistsUsecase: BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue>,
                                     private val getPlaylistItemsUsecase: BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>,
                                     private val removePlaylistUsecase: BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue>) :
    BaseViewModel() {
    fun fetchPlaylistAndRecently(playlistCallback: (List<PlaylistEntity>) -> Unit,
                                 recentlyCallback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistsUsecase) {
            onNext {
                // All playlist without the history playlist.
                playlistCallback(it.toMutableList().apply { removeAt((Constant.DATABASE_PLAYLIST_HISTORY_ID - 1)) })
            }
            lifecycleProvider.execute(getPlaylistItemsUsecase,
                GetPlaylistItemsUsecase.RequestValue(DATABASE_PLAYLIST_HISTORY_ID.toLong())) {
                onNext(recentlyCallback)
            }
        }
    }

    fun deletePlaylist(playlist: PlaylistEntity, callback: (Boolean) -> Unit) {
        lifecycleProvider.execute(removePlaylistUsecase, AddPlaylistUsecase.RequestValue(playlist)) {
            onNext(callback)
        }
    }
}