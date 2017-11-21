package taiwan.no1.app.ssfm.models.usecases.v2

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicRankUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/21/17
 */
class GetMusicRankUsecase(repository: IDataStore) : BaseUsecase<MusicRankEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<MusicRankEntity> =
        (parameters ?: RequestValue()).let { repository.fetchRankMusic(it.rankType) }

    data class RequestValue(val rankType: Int = 0) : RequestValues
}