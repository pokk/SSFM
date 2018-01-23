package taiwan.no1.app.ssfm.models.usecases.v2

import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicUriUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/21/17
 */
class GetMusicUriUsecase(repository: IDataStore) : BaseUsecase<MusicEntity, RequestValue>(repository) {
    override fun fetchUsecase() =
        (parameters ?: RequestValue()).let {
            repository.searchMusic("${it.playlistItem.artistName} ${it.playlistItem.trackName}", 0)
        }

    data class RequestValue(val playlistItem: PlaylistItemEntity = PlaylistItemEntity()) : RequestValues
}