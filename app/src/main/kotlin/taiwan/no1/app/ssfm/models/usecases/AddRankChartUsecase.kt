package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.usecases.AddRankChartUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/25/17
 */
class AddRankChartUsecase(repository: IDataStore) : BaseUsecase<Boolean, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: RequestValue()).let { repository.addRankChart(it.entity) }

    data class RequestValue(val entity: RankChartEntity = RankChartEntity()) : RequestValues
}