package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetAlbumInfoCase(repository: IDataStore): BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<AlbumEntity> =
        (parameters ?: GetAlbumInfoCase.RequestValue()).let { repository.getAlbumInfo(it.artist, it.albumOrMbid) }

    data class RequestValue(val artist: String = "", val albumOrMbid: String = ""): RequestValues
}