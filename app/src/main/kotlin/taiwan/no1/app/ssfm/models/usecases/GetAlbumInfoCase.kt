package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.usecases.GetAlbumInfoCase.RequestValue

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetAlbumInfoCase(repository: IDataStore) : BaseUsecase<AlbumEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<AlbumEntity> =
        (parameters ?: RequestValue()).let { repository.getAlbumInfo(it.artist, it.albumOrMbid) }

    data class RequestValue(val artist: String = "", val albumOrMbid: String = "") : RequestValues
}