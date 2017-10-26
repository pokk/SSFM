package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTrackEntity

/**
 * @author  jieyi
 * @since   10/26/17
 */
class GetTagTopTracksCase(repository: IDataStore):
    BaseUsecase<TopTrackEntity, GetTagTopTracksCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopTrackEntity> =
        (parameters ?: GetTagTopTracksCase.RequestValue()).let { repository.getTagTopTracks(it.tag, it.page, it.limit) }

    data class RequestValue(val tag: String = "", val page: Int = 1, val limit: Int = 20): RequestValues
}