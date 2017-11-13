package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class RemoveKeywordHistoriesCase(repository: IDataStore) :
    BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: RequestValue()).let { repository.removeKeywords(it.keyword) }

    data class RequestValue(val keyword: String = "") : RequestValues
}