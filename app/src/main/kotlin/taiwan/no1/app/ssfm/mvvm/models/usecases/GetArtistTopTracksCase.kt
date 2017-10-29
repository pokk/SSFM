package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopTrackEntity

/**
 * @author  jieyi
 * @since   10/29/17
 */
class GetArtistTopTracksCase(repository: IDataStore):
    BaseUsecase<ArtistTopTrackEntity, GetArtistTopTracksCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<ArtistTopTrackEntity> = repository.getArtistTopTrack(parameters?.name ?: "")

    data class RequestValue(val name: String = ""): RequestValues
}