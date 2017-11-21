package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.models.usecases.GetTopAlbumsCase.RequestValue

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopAlbumsCase(repository: IDataStore) :
    BaseUsecase<TopAlbumEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopAlbumEntity> = repository.getTagTopAlbums(parameters?.name ?: "")

    data class RequestValue(val name: String = "") : RequestValues
}