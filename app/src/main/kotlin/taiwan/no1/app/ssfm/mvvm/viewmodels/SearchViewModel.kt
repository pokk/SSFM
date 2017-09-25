package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.observer
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.hideSoftKeyboard
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase.RequestValue
import taiwan.no1.app.ssfm.mvvm.views.activities.SearchActivity

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(private val activity: SearchActivity,
                      private val usecase: BaseUsecase<SearchMusicEntity, RequestValue>):
    BaseViewModel(activity) {
    /** Menu Title */
    var title = ObservableField<String>()
    /** Check search view is clicked or un-clicked */
    var isSearching = ObservableBoolean()

    init {
        title.set(activity.getString(R.string.menu_search))
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
    fun openSearchView(view: View) {
        isSearching.set(true)
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
        // HACK(jieyi): 9/25/17 debounce the continue click.
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
            activity.navigate<String>(RxBusConstant.FRAGMENT_SEARCH_HISTORY)
        }
        else {
            // TODO(jieyi): 9/25/17 `debounce` the suggestion list.
        }

        return true
    }

    /**
     * Retrieve the data more by querying to the remote.
     *
     * @param query the query of song's or singer's name for searching a music.
     * @param page the page number of the total result.
     * @param pageSize the result quantity of each of the queries.
     */
    fun queryMoreResult(query: String, page: Int = 1, pageSize: Int = 20) =
        usecase.execute(SearchMusicCase.RequestValue(query, page, pageSize),
            observer<SearchMusicEntity>().
                onNext { RxBus.get().post(RxBusConstant.FRAGMENT_SEARCH_RESULT, it) }.
                onError { loge(it) })
}