package taiwan.no1.app.ssfm.models.usecases.v2

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.SongListEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase

/**
 * @author  jieyi
 * @since   11/21/17
 */
class GetSongListCase(repository: IDataStore) : BaseUsecase<SongListEntity, GetSongListCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<SongListEntity> =
        (parameters ?: RequestValue()).let { repository.fetchPlaylistDetail(it.hashCode) }

    data class RequestValue(val hashCode: String = "") : RequestValues
}