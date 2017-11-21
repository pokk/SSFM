package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopTracksCase.RequestValue

/**
 * @author  jieyi
 * @since   10/29/17
 */
class GetArtistTopTracksCase(repository: IDataStore) : BaseUsecase<ArtistTopTrackEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<ArtistTopTrackEntity> =
        (parameters ?: RequestValue()).let { repository.getArtistTopTrack(it.name) }

    data class RequestValue(val name: String = "") : RequestValues
}