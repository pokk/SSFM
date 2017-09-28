package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.usecases.UpdateKeywordHistoryCase.RequestValue

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class UpdateKeywordHistoryCase(repository: IDataStore): BaseUsecase<Boolean, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Boolean> = repository.updateKeyword(parameters?.keyword ?: "")

    class RequestValue(val keyword: String): RequestValues
}