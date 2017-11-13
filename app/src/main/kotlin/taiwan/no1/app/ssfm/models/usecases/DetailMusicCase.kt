package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class DetailMusicCase(repository: IDataStore) : BaseUsecase<DetailMusicEntity, DetailMusicCase.RequestValue>(
    repository) {
    override fun fetchUsecase(): Observable<DetailMusicEntity> =
        (parameters ?: RequestValue()).let { repository.getDetailMusicRes(it.hashCode) }

    data class RequestValue(val hashCode: String = "") : BaseUsecase.RequestValues
}