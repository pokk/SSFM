package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.observer
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.hideSoftKeyboard
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SaveKeywordHistoryCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase
import taiwan.no1.app.ssfm.mvvm.views.activities.SearchActivity

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(private val activity: SearchActivity,
                      private val searchUsecase: BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue>,
                      private val addHistoryUsecase: BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue>,
                      private val getHistoriesUsecase: BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>,
                      private val deleteHistoriesUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>):
    BaseViewModel(activity) {
    /** Menu Title */
    var title = ObservableField<String>()
    /** Check search view is clicked or un-clicked */
    var isSearching = ObservableBoolean()
    private var keyword = ""

    init {
        title.set(activity.getString(R.string.menu_search))
    }

    override fun onAttach() {
        RxBus.get().register(this)
    }

    override fun onDetach() {
        RxBus.get().unregister(this)
    }

    /**
     * The action of closing the search view.
     */
    fun closeSearchView(): Boolean {
        isSearching.set(false)
        activity.navigate<String>(RxBusConstant.FRAGMENT_SEARCH_INDEX)
        return false
    }

    /**
     * The action of opening the search view.
     *
     * @param view
     */
    fun openSearchView(view: View?) {
        isSearching.set(true)
        // TODO(jieyi): 9/29/17 Added the limitation.
        getHistoriesUsecase.execute(observer { })
        activity.navigate<String>(RxBusConstant.FRAGMENT_SEARCH_HISTORY)
    }

    /**
     * The action of submitting the search query.
     *
     * @param query the query of song's or singer's name for searching a music.
     */
    fun querySubmit(query: String): Boolean {
        context.hideSoftKeyboard()
        activity.navigate<String>(RxBusConstant.FRAGMENT_SEARCH_RESULT)
        keyword = query
        addHistoryUsecase.execute(SaveKeywordHistoryCase.RequestValue(keyword),
            observer { logd("insert success: $it") })
        queryMoreResult(query)

        return true
    }

    /**
     * The action of the search query's newText is changed.
     *
     * @param newText inputting by the user in the search view.
     */
    fun textChanged(newText: String): Boolean {
        // When clearing the text in the search view.
        if (newText.isBlank()) {
            openSearchView(null)
        }
        else {
            // TODO(jieyi): 9/25/17 `debounce` the suggestion list.
            if (keyword != newText) {
                activity.navigate<String>(RxBusConstant.FRAGMENT_SEARCH_HISTORY)
            }
        }

        return true
    }

    /**
     * Retrieve the data more by querying to the remote.
     *
     * @param query the query of song's or singer's name for searching a music.
     * @param page the page number of the total result.
     * @param pageSize the result quantity of each of the queries.
     *
     * @to [taiwan.no1.app.ssfm.mvvm.views.fragments.SearchResultFragment.receiveMusicRes]
     */
    fun queryMoreResult(query: String, page: Int = 1, pageSize: Int = Constant.QUERY_PAGE_SIZE) =
        searchUsecase.execute(SearchMusicCase.RequestValue(query, page, pageSize),
            observer {
                RxBus.get().post(RxBusConstant.FRAGMENT_SEARCH_RESULT,
                    hashMapOf(RxBusConstant.HASH_MORE_DATA_INIT to (1 == page),
                        RxBusConstant.HASH_MORE_DATA_ENTITY to it))
            })

    /**
     * For retrieving more music data.
     *
     * @param total the number of the current total items.
     *
     * @from [taiwan.no1.app.ssfm.mvvm.views.fragments.SearchResultFragment.recyclerViewScrollListener]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.QUERY_LOAD_MORE)))
    fun loadMoreResult(total: Number) =
        queryMoreResult(keyword, Math.ceil((total as Int) / Constant.QUERY_PAGE_SIZE.toDouble()).toInt() + 1)
}