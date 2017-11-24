package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity

/**
 * @author  jieyi
 * @since   24/11/17
 */
class GetTopChartsUsecase(repository: IDataStore) : FetchRankChartCase(repository) {
    override fun fetchUsecase(): Observable<List<RankChartEntity>> = repository.getChartTop()
}