package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TopArtistEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopArtistsCase(repository: IDataStore) : BaseUsecase<TopArtistEntity, GetTopArtistsCase.RequestValue>(
    repository) {
    override fun fetchUsecase(): Observable<TopArtistEntity> =
        (parameters ?: RequestValue()).let { repository.getChartTopArtist(it.page, it.limit) }

    data class RequestValue(val page: Int = 1, val limit: Int = 20) : RequestValues
}