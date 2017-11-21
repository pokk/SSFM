package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.usecases.GetKeywordHistoriesCase.RequestValue

/**
 * @author  jieyi
 * @since   8/14/17
 */
class GetKeywordHistoriesCase(repository: IDataStore) : BaseUsecase<List<KeywordEntity>, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<KeywordEntity>> =
        (parameters ?: RequestValue()).let { repository.getKeywords(it.size) }

    data class RequestValue(val size: Int = -1) : RequestValues
}