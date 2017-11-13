package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   11/8/17
 */
class EditPlaylistUsecase(repository: IDataStore) :
    BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: AddPlaylistUsecase.RequestValue()).let { repository.editPlaylist(it.entity) }
}