package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopAlbumsUsecase.RequestValue

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetArtistTopAlbumsUsecase(repository: IDataStore) : BaseUsecase<ArtistTopAlbumEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<ArtistTopAlbumEntity> =
        (parameters ?: RequestValue()).let { repository.getArtistTopAlbum(it.name) }

    data class RequestValue(val name: String = "") : RequestValues
}