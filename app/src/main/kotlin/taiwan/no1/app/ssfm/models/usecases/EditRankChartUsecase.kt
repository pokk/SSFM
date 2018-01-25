package taiwan.no1.app.ssfm.models.usecases

import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.usecases.AddRankChartUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/25/17
 */
class EditRankChartUsecase(repository: IDataStore) : BaseUsecase<Boolean, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: RequestValue()).let {
            loge(parameters)
            logd(it.entity, it.entity.hashCode())
            repository.editRankChart(it.entity)
        }
}