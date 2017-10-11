package taiwan.no1.app.ssfm.mvvm.models.usecases

import de.umass.lastfm.Artist
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopArtistsCase(repository: IDataStore): BaseUsecase<Collection<Artist>, GetTopArtistsCase.RequestValue>(
    repository) {
    override fun fetchUsecase(): Observable<Collection<Artist>> = repository.getChartTopArtist(parameters?.page ?: 1)

    data class RequestValue(val page: Int = -1): RequestValues
}