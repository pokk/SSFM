package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.DetailMusicCase.RequestValue

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class DetailMusicCase(repository: IDataStore): BaseUsecase<DetailMusicEntity, RequestValue>(repository) {
    override fun fetchUsecase(): Observable<DetailMusicEntity> =
        repository.getDetailMusicRes(parameters?.hashCode ?: "")

    data class RequestValue(val hashCode: String): BaseUsecase.RequestValues
}