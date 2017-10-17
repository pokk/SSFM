package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopAlbumsCase(repository: IDataStore):
    BaseUsecase<ArtistTopAlbumEntity, GetTopAlbumsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<ArtistTopAlbumEntity> = repository.getArtistTopAlbum(parameters?.name ?: "")

    data class RequestValue(val name: String = ""): RequestValues
}