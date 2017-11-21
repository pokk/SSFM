package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.models.usecases.GetTagTopTracksCase.RequestValue

/**
 * @author  jieyi
 * @since   10/26/17
 */
class GetTagTopTracksCase(repository: IDataStore) : BaseUsecase<TopTrackEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopTrackEntity> =
        (parameters ?: RequestValue()).let { repository.getTagTopTracks(it.tag, it.page, it.limit) }

    data class RequestValue(val tag: String = "", val page: Int = 1, val limit: Int = 20) : RequestValues
}