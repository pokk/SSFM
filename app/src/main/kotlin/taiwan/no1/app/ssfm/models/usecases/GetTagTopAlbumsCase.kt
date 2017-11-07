package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopAlbumEntity

/**
 * @author  jieyi
 * @since   10/26/17
 */
class GetTagTopAlbumsCase(repository: IDataStore):
    BaseUsecase<TopAlbumEntity, GetTagTopAlbumsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopAlbumEntity> =
        (parameters ?: GetTagTopAlbumsCase.RequestValue()).let { repository.getTagTopAlbums(it.tag, it.page, it.limit) }

    data class RequestValue(val tag: String = "", val page: Int = 1, val limit: Int = 20): RequestValues
}
