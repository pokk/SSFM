package taiwan.no1.app.ssfm.mvvm.models.usecases

import de.umass.lastfm.Image
import de.umass.lastfm.PaginatedResult
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetArtistImagesCase(repository: IDataStore): BaseUsecase<PaginatedResult<Image>, GetArtistImagesCase.RequestValue>(
    repository) {
    override fun fetchUsecase(): Observable<PaginatedResult<Image>> = repository.getArtistImages(parameters?.mbid ?: "")

    data class RequestValue(val mbid: String): RequestValues
}