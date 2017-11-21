package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.models.usecases.GetTagTopAlbumsCase.RequestValue

/**
 * @author  jieyi
 * @since   10/26/17
 */
class GetTagTopAlbumsCase(repository: IDataStore) : BaseUsecase<TopAlbumEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopAlbumEntity> =
        (parameters ?: RequestValue()).let { repository.getTagTopAlbums(it.tag, it.page, it.limit) }

    data class RequestValue(val tag: String = "", val page: Int = 1, val limit: Int = 20) : RequestValues
}
