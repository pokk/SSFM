package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity

/**
 * @author  jieyi
 * @since   11/5/17
 */
class GetTagInfoCase(repository: IDataStore): BaseUsecase<TagEntity, GetTagInfoCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TagEntity> =
        (parameters ?: GetTagInfoCase.RequestValue()).let { repository.getTagInfo(it.tag) }

    data class RequestValue(val tag: String = ""): RequestValues
}