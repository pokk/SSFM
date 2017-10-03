package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase.RequestValue

/**
 * @author  jieyi
 * @since   8/14/17
 */
class GetKeywordHistoriesCase(repository: IDataStore): BaseUsecase<List<KeywordEntity>, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<KeywordEntity>> = repository.getKeywords(parameters?.size ?: -1)

    data class RequestValue(val size: Int = -1): RequestValues
}