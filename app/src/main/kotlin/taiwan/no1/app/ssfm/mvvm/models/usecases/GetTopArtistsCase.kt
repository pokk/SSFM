package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopArtistsCase(repository: IDataStore): BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>(
    repository) {
    override fun fetchUsecase(): Observable<ChartTopArtistEntity> =
        (parameters ?: GetTopArtistsCase.RequestValue()).let { repository.getChartTopArtist(it.page, it.limit) }

    data class RequestValue(val page: Int = 1, val limit: Int = 20): RequestValues
}