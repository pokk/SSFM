package taiwan.no1.app.ssfm.models.usecases.v2

import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/21/17
 */
class SearchMusicUsecase(repository: IDataStore) : BaseUsecase<MusicEntity, RequestValue>(repository) {
    override fun fetchUsecase() =
        (parameters ?: RequestValue()).let { repository.searchMusic(it.keyword, it.page) }

    data class RequestValue(val keyword: String = "", val page: Int = 0) : RequestValues
}