package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetArtistInfoCase(repository: IDataStore): BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<ArtistEntity> =
        (parameters ?: GetArtistInfoCase.RequestValue()).let { repository.getArtistInfo(it.Mbid, it.artist) }

    data class RequestValue(val artist: String = "", val Mbid: String = ""): RequestValues
}