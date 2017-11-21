package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.models.usecases.GetDetailMusicUsecase.RequestValue

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
@Deprecated("There is a better api for searching a music, please check v2")
class GetDetailMusicUsecase(repository: IDataStore) : BaseUsecase<DetailMusicEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<DetailMusicEntity> =
        (parameters ?: RequestValue()).let { repository.getDetailMusicRes(it.hashCode) }

    data class RequestValue(val hashCode: String = "") : BaseUsecase.RequestValues
}