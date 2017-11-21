package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.models.usecases.GetTopTracksCase.RequestValue

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopTracksCase(repository: IDataStore) : BaseUsecase<TopTrackEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopTrackEntity> =
        (parameters ?: RequestValue()).let { repository.getChartTopTracks(it.page, it.limit) }

    data class RequestValue(val page: Int = 1, val limit: Int = 20) : RequestValues
}