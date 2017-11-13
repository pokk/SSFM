package taiwan.no1.app.ssfm.functions.playlist

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * @author  jieyi
 * @since   11/14/17
 */
class PlaylistDetailFragmentViewModel(private val getPlaylistItemsUsecase: BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>) :
    BaseViewModel() {
    fun fetchPlaylistItems(callback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistItemsUsecase) {
            onNext {
            }
        }
    }
}