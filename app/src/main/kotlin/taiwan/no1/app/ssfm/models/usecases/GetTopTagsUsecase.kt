package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTagEntity
import taiwan.no1.app.ssfm.models.usecases.GetTopTagsUsecase.RequestValue

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopTagsUsecase(repository: IDataStore) : BaseUsecase<TopTagEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopTagEntity> =
        (parameters ?: RequestValue()).let { repository.getChartTopTags() }

    data class RequestValue(val page: Int = 1, val limit: Int = 30) : RequestValues
}