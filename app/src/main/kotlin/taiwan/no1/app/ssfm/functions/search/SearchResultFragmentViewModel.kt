package taiwan.no1.app.ssfm.functions.search

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicCase

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragmentViewModel(private val searchUsecase: BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue>) :
    BaseViewModel() {
    fun sendSearchRequest(keyword: String,
                          page: Int = 1,
                          pageSize: Int = Constant.QUERY_PAGE_SIZE,
                          resultCallback: (keyword: String, musics: MutableList<InfoBean>, canLoadMore: Boolean) -> Unit) =
        if (keyword.isNotBlank()) {
            lifecycleProvider.execute(searchUsecase,
                SearchMusicCase.RequestValue(keyword, page, pageSize)) {
                onNext {
                    // Raise the stop loading more data flag.
                    val loadMoreFlag = Constant.QUERY_PAGE_SIZE <= (it.data?.info?.size ?: 0)

                    it.data?.info?.toObservable()?.
                        filter { (it.singername?.isNotEmpty() == true) && (it.songname?.isNotEmpty() == true) }?.
                        subscribeOn(Schedulers.io())?.
                        toList()?.observeOn(AndroidSchedulers.mainThread())?.subscribe { list, _ ->
                        resultCallback(keyword, list, loadMoreFlag)
                    } ?: resultCallback(keyword, mutableListOf(), loadMoreFlag)
                }
            }
        }
        else {
            // This situation is the first click `search view` or keyword is blank.
            resultCallback(keyword, mutableListOf(), false)
        }
}