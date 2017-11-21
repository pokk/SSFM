package taiwan.no1.app.ssfm.models.usecases.v2

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.HotPlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetHotPlaylistUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/21/17
 */
class GetHotPlaylistUsecase(repository: IDataStore) : BaseUsecase<HotPlaylistEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<HotPlaylistEntity> =
        (parameters ?: RequestValue()).let { repository.fetchHotPlaylist(it.page) }

    data class RequestValue(val page: Int = 0) : RequestValues
}