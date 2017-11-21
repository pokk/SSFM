package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/8/17
 */
class GetPlaylistItemsUsecase(repository: IDataStore) : BaseUsecase<List<PlaylistItemEntity>, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<PlaylistItemEntity>> =
        (parameters ?: GetPlaylistItemsUsecase.RequestValue()).let { repository.getPlaylistItems(it.playlistId) }

    data class RequestValue(val playlistId: Long = -1) : RequestValues
}