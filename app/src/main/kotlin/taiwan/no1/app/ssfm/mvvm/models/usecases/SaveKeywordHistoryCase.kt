package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.usecases.SaveKeywordHistoryCase.RequestValue

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class SaveKeywordHistoryCase(repository: IDataStore): BaseUsecase<Boolean, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> = repository.saveKeyword(parameters?.keyword ?: "")

    data class RequestValue(val keyword: String): RequestValues
}