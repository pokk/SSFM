package taiwan.no1.app.ssfm.models.usecases.v2

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase.RequestValue

/**
 * @author  jieyi
 * @since   11/21/17
 */
class SearchMusicUsecase(repository: IDataStore) : BaseUsecase<MusicEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<MusicEntity> =
        (parameters ?: RequestValue()).let { repository.searchMusic(it.keyword) }

    data class RequestValue(val keyword: String = "") : RequestValues
}