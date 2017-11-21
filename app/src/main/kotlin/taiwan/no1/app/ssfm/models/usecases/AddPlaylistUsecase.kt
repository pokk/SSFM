package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/8/17
 */
class AddPlaylistUsecase(repository: IDataStore) : BaseUsecase<PlaylistEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<PlaylistEntity> =
        (parameters ?: RequestValue()).let { repository.addPlaylist(it.entity) }

    data class RequestValue(val entity: PlaylistEntity = PlaylistEntity()) : RequestValues
}