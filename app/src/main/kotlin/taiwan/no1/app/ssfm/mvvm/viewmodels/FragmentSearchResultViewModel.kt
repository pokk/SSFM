package taiwan.no1.app.ssfm.mvvm.viewmodels

import com.devrapid.kotlinknifer.observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase.RequestValue

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchResultViewModel(private val searchUsecase: BaseUsecase<SearchMusicEntity, RequestValue>):
    BaseViewModel() {
    fun sendSearchRequest(keyword: String,
                          page: Int = 1,
                          pageSize: Int = Constant.QUERY_PAGE_SIZE,
                          resultCallback: (keyword: String, musics: MutableList<InfoBean>, canLoadMore: Boolean) -> Unit) =
        if (keyword.isNotBlank()) {
            searchUsecase.execute(RequestValue(keyword, page, pageSize),
                observer {
                    // Raise the stop loading more data flag.
                    val loadMoreFlag = Constant.QUERY_PAGE_SIZE <= (it.data?.info?.size ?: 0)

                    it.data?.info?.toObservable()?.
                        filter { (it.singername?.isNotEmpty() == true) && (it.songname?.isNotEmpty() == true) }?.
                        subscribeOn(Schedulers.io())?.
                        toList()?.observeOn(AndroidSchedulers.mainThread())?.
                        subscribe { list, _ -> resultCallback(keyword, list, loadMoreFlag) }
                        ?: resultCallback(keyword, mutableListOf(), loadMoreFlag)
                })
        }
        else {
            // This situation is the first click `search view` or keyword is blank.
            resultCallback(keyword, mutableListOf(), false)
        }
}