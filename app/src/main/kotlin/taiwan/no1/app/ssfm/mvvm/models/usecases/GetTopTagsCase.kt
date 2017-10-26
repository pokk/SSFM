package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTagEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopTagsCase(repository: IDataStore): BaseUsecase<TopTagEntity, GetTopTagsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopTagEntity> =
        (parameters ?: GetTopTagsCase.RequestValue()).let { repository.getChartTopTags() }

    data class RequestValue(val page: Int = 1, val limit: Int = 30): RequestValues
}