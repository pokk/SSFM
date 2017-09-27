package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase.RequestValue

/**
 * A usecase for searching the music by the song's or the singer's name.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class SearchMusicCase(repository: IDataStore): BaseUsecase<SearchMusicEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<SearchMusicEntity> =
        repository.getSearchMusicRes(parameters?.singerOrSongName ?: "")

    data class RequestValue(val singerOrSongName: String,
                            val page: Int = 1,
                            val pageSize: Int = Constant.QUERY_PAGE_SIZE): RequestValues
}