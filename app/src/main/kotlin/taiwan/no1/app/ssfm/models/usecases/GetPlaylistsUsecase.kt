package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase

/**
 * @author  jieyi
 * @since   11/8/17
 */
class GetPlaylistsUsecase(repository: IDataStore):
    BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<PlaylistEntity>> = repository.getPlaylists()
}