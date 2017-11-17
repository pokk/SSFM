package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity

/**
 * @author  jieyi
 * @since   11/8/17
 */
class AddPlaylistUsecase(repository: IDataStore) :
    BaseUsecase<PlaylistEntity, AddPlaylistUsecase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<PlaylistEntity> =
        (parameters ?: AddPlaylistUsecase.RequestValue()).let { repository.addPlaylist(it.entity) }

    data class RequestValue(val entity: PlaylistEntity = PlaylistEntity()) : RequestValues
}