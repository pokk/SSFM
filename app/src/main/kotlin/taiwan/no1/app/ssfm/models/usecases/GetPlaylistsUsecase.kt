package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/8/17
 */
class GetPlaylistsUsecase(repository: IDataStore) : BaseUsecase<List<PlaylistEntity>, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<PlaylistEntity>> = repository.getPlaylists()
}