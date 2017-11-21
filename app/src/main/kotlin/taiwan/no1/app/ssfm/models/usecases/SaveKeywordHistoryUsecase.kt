package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.usecases.SaveKeywordHistoryUsecase.RequestValue

/**
 * @author  jieyi
 * @since   8/14/17
 */
class SaveKeywordHistoryUsecase(repository: IDataStore) : BaseUsecase<Boolean, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: RequestValue()).let { repository.insertKeyword(it.keyword) }

    data class RequestValue(val keyword: String = "") : RequestValues
}