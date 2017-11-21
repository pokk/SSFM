package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.models.usecases.SearchMusicUsecase.RequestValue

/**
 * A usecase for searching the music by the song's or the singer's name.
 *
 * @author  jieyi
 * @since   8/14/17
 */
@Deprecated("There is a better api for searching a music, please check v2")
class SearchMusicUsecase(repository: IDataStore) : BaseUsecase<SearchMusicEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<SearchMusicEntity> =
        (parameters ?: RequestValue()).let { repository.getSearchMusicRes(it.singerOrSongName, it.page, it.pageSize) }

    data class RequestValue(val singerOrSongName: String = "",
                            val page: Int = 1,
                            val pageSize: Int = Constant.QUERY_PAGE_SIZE) : RequestValues
}