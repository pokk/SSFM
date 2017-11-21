package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoUsecase.RequestValue

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetArtistInfoUsecase(repository: IDataStore) : BaseUsecase<ArtistEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<ArtistEntity> =
        (parameters ?: RequestValue()).let { repository.getArtistInfo(it.Mbid, it.artist) }

    data class RequestValue(val artist: String = "", val Mbid: String = "") : RequestValues
}