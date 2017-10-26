package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopArtistEntity

/**
 * @author  jieyi
 * @since   10/26/17
 */
class GetTagTopArtistsCase(repository: IDataStore):
    BaseUsecase<TopArtistEntity, GetTagTopArtistsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopArtistEntity> =
        (parameters ?: GetTagTopArtistsCase.RequestValue()).let {
            repository.getTagTopArtists(it.tag, it.page, it.limit)
        }

    data class RequestValue(val tag: String = "", val page: Int = 1, val limit: Int = 20): RequestValues
}