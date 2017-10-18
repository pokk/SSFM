package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopTracksCase(repository: IDataStore): BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue>(
    repository) {
    override fun fetchUsecase(): Observable<ChartTopTrackEntity> =
        (parameters ?: GetTopTracksCase.RequestValue()).let { repository.getChartTopTracks(it.page, it.limit) }

    data class RequestValue(val page: Int = 1, val limit: Int = 20): RequestValues
}