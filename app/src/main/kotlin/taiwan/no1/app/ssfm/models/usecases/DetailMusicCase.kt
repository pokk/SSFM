package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable

/**
 * A usecase for retrieving the detail of a music.
 *
 * @author  jieyi
 * @since   8/14/17
 */
class DetailMusicCase(repository: IDataStore): BaseUsecase<DetailMusicEntity, DetailMusicCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<DetailMusicEntity> =
        (parameters ?: DetailMusicCase.RequestValue()).let { repository.getDetailMusicRes(it.hashCode) }

    data class RequestValue(val hashCode: String = ""): BaseUsecase.RequestValues
}