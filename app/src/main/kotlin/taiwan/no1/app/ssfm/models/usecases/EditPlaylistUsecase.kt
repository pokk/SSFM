package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/8/17
 */
class EditPlaylistUsecase(repository: IDataStore) : EditPlaylistCase(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: RequestValue()).let { repository.editPlaylist(it.entity) }
}