package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.usecases.DetailMusicRes.RequestValue

/**
 *
 * @author  jieyi
 * @since   8/14/17
 */
class DetailMusicRes(repository: IDataStore): BaseUsecase<RequestValue>(repository) {
    override fun <T> fetchUsecase(): Observable<T> {
        return this.repository.getDetailMusicRes(this.parameters.id.toString())
    }
//    override fun fetchUsecase() =
//        this.repository.getDetailMusicRes(this.parameters.id.toString())

    data class RequestValue(var id: Int): BaseUsecase.RequestValues
}