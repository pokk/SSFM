package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   8/14/17
 */
class SaveKeywordHistoryCase(repository: IDataStore):
    BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> =
        (parameters ?: SaveKeywordHistoryCase.RequestValue()).let { repository.insertKeyword(it.keyword) }

    data class RequestValue(val keyword: String = ""): RequestValues
}