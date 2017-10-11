package taiwan.no1.app.ssfm.mvvm.models.usecases

import de.umass.lastfm.Album
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopAlbumsCase(repository: IDataStore): BaseUsecase<Collection<Album>, GetTopAlbumsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<Collection<Album>> = repository.getArtistTopAlbum(parameters?.name ?: "")

    data class RequestValue(val name: String = ""): RequestValues
}