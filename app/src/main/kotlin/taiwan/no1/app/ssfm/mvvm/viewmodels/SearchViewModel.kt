package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.SparseArray
import android.view.View
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.observer
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.hideSoftKeyboard
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SaveKeywordHistoryCase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(private val context: Context,
//                      private val searchUsecase: BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue>,
                      private val addHistoryUsecase: BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue>):
    BaseViewModel() {
    /** Menu Title */
    var title = ObservableField<String>()
    /** Check search view is clicked or un-clicked */
    var isSearching = ObservableBoolean()
    /** For navigating to other fragments from the parent activity */
    lateinit var navigateListener: (fragmentTag: String, params: SparseArray<Any>?) -> Unit
    private var keyword = ""

    init {
        title.set(context.getString(R.string.menu_search))
    }

    //region Action from View
    /**
     * The action of closing the search view.
     */
    fun closeSearchView(): Boolean {
        isSearching.set(false)
        navigateListener(RxBusConstant.FRAGMENT_SEARCH_INDEX, null)
        return false
    }

    /**
     * The action of opening the search view.
     *
     * @param view
     */
    fun openSearchView(view: View?) {
        isSearching.set(true)
        navigateListener(RxBusConstant.FRAGMENT_SEARCH_HISTORY, null)
    }

    /**
     * The action of submitting the search query.
     *
     * @param query the query of song's or singer's name for searching a music.
     */
    fun querySubmit(query: String): Boolean {
        context.hideSoftKeyboard()
        navigateListener(RxBusConstant.FRAGMENT_SEARCH_RESULT, SparseArray<Any>().apply { put(0, query) })
        keyword = query
        addHistoryUsecase.execute(SaveKeywordHistoryCase.RequestValue(keyword),
            observer { logd("insert success: $it") })

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
                navigateListener(RxBusConstant.FRAGMENT_SEARCH_HISTORY, null)
            }
        }

        return true
    }
    //endregion
}