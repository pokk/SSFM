package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase

/**
 * @author  jieyi
 * @since   11/8/17
 */
class AddPlaylistItemUsecase(repository: IDataStore):
    BaseUsecase<Boolean, AddPlaylistItemUsecase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: AddPlaylistItemUsecase.RequestValue()).let { repository.addPlaylistItem(it.entity) }

    data class RequestValue(val entity: PlaylistItemEntity = PlaylistItemEntity()): RequestValues
}