package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoryCase.RequestValue

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class GetKeywordHistoryCase(repository: IDataStore): BaseUsecase<List<KeywordEntity>, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<List<KeywordEntity>> = repository.getKeywords()

    class RequestValue: RequestValues
}