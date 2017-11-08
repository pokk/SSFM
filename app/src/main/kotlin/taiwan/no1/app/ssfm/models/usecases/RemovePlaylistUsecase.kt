package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   11/8/17
 */
class RemovePlaylistUsecase(repository: IDataStore):
    BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: AddPlaylistUsecase.RequestValue()).let { repository.removePlaylist(it.entity) }
}