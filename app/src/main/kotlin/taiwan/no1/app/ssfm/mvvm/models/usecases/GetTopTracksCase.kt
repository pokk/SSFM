package taiwan.no1.app.ssfm.mvvm.models.usecases

import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopTracksCase(repository: IDataStore): BaseUsecase<Collection<Track>, GetTopTracksCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Collection<Track>> = repository.getChartTopTracks(parameters?.page ?: 1)

    data class RequestValue(val page: Int = -1): RequestValues
}