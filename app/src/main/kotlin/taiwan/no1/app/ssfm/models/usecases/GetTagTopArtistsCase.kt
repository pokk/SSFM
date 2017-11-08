package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TagTopArtistEntity

/**
 * @author  jieyi
 * @since   10/26/17
 */
class GetTagTopArtistsCase(repository: IDataStore):
    BaseUsecase<TagTopArtistEntity, GetTagTopArtistsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TagTopArtistEntity> =
        (parameters ?: RequestValue()).let {
            repository.getTagTopArtists(it.tag, it.page, it.limit)
        }

    data class RequestValue(val tag: String = "", val page: Int = 1, val limit: Int = 20): RequestValues
}