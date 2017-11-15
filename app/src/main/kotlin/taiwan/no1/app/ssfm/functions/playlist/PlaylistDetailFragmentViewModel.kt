package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableField
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
    val playlistName by lazy { ObservableField<String>() }
    val trackQuantity by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }

    fun fetchPlaylistItems(playlistId: Long, callback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistItemsUsecase, GetPlaylistItemsUsecase.RequestValue(playlistId)) {
            onNext(callback)
        }
    }
}