package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase

/**
 * @author  jieyi
 * @since   11/8/17
 */
class RemovePlaylistItemUsecase(repository: IDataStore):
    BaseUsecase<Boolean, AddPlaylistItemUsecase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: AddPlaylistItemUsecase.RequestValue()).let { repository.removePlaylistItem(it.entity) }
}