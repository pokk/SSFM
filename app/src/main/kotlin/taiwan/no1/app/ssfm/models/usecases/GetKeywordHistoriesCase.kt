package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.KeywordEntity

/**
 * @author  jieyi
 * @since   8/14/17
 */
class GetKeywordHistoriesCase(repository: IDataStore):
    BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<KeywordEntity>> =
        (parameters ?: GetKeywordHistoriesCase.RequestValue()).let { repository.getKeywords(it.size) }

    data class RequestValue(val size: Int = -1): RequestValues
}