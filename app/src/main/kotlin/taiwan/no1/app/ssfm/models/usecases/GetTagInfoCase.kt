package taiwan.no1.app.ssfm.models.usecases

import io.reactivex.Observable
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity

/**
 * @author  jieyi
 * @since   11/5/17
 */
class GetTagInfoCase(repository: IDataStore): BaseUsecase<TagEntity, GetTagInfoCase.RequestValue>(repository) {
    override fun fetchUsecase(): Observable<TagEntity> =
        (parameters ?: RequestValue()).let { repository.getTagInfo(it.tag) }

    data class RequestValue(val tag: String = ""): RequestValues
}