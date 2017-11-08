package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopAlbumEntity

/**
 * @author  jieyi
 * @since   10/11/17
 */
class GetTopAlbumsCase(repository: IDataStore):
    BaseUsecase<TopAlbumEntity, GetTopAlbumsCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TopAlbumEntity> = repository.getTagTopAlbums(parameters?.name ?: "")

    data class RequestValue(val name: String = ""): RequestValues
}