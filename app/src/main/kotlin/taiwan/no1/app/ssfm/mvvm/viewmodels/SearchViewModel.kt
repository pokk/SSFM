package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.SparseArray
import android.view.View
import com.devrapid.kotlinknifer.logd
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.hideSoftKeyboard
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SaveKeywordHistoryCase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(private val context: Context,
                      private val addHistoryUsecase: BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue>):
    BaseViewModel() {
    lateinit var navigateListener: (fragmentTag: String, params: SparseArray<Any>?) -> Unit
    /** Menu Title */
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_search)) }
    /** The keyword of a song or singer's name */
    val keyword by lazy { ObservableField<String>("") }
    /** Check search view is clicked or un-clicked */
    val isSearching by lazy { ObservableBoolean() }

    /** For navigating to other fragments from the parent activity */


    //region Lifecycle
    override fun <E> onAttach(lifecycleProvider: LifecycleProvider<E>) {
        super.onAttach(lifecycleProvider)
        RxBus.get().register(this)
    }

    override fun onDetach() {
        RxBus.get().unregister(this)
    }
    //endregion

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
        // FIXME(jieyi): 10/2/17 When `search` music, here will be called twice. From `history`, it won't.
        context.hideSoftKeyboard()
        navigateListener(RxBusConstant.FRAGMENT_SEARCH_RESULT, SparseArray<Any>().apply { put(0, query) })
        keyword.set(query)
        lifecycleProvider.execute(addHistoryUsecase, SaveKeywordHistoryCase.RequestValue(keyword.get())) {
            onNext { logd(it) }
        }

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
            if (keyword.get() != newText) {
                navigateListener(RxBusConstant.FRAGMENT_SEARCH_HISTORY, null)
            }
        }

        return true
    }
    //endregion

    /**
     * The click event when clicking a item to the history list.
     *
     * @param keyword of the song or singer's name.
     *
     * @event_from [taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewSearchHistoryViewModel.selectHistoryItem]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.VIEWMODEL_CLICK_HISTORY)))
    fun receiveClickHistoryEvent(keyword: String) {
        querySubmit(keyword)
    }
}