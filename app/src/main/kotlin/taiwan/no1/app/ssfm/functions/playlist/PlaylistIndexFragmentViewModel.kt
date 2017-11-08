package taiwan.no1.app.ssfm.functions.playlist

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class PlaylistIndexFragmentViewModel(val getPlaylistsUsecase: BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue>,
                                     val getPlaylistItemsUsecase: BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>):
    BaseViewModel() {
    fun fetchPlaylistAndRecently(playlistCallback: (List<PlaylistEntity>) -> Unit,
                                 recentlyCallback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistsUsecase) {
            onNext {
                playlistCallback(it)
//                lifecycleProvider.execute(getPlaylistItemsUsecase) { onNext(recentlyCallback) }
            }
        }
    }
}