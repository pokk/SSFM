package taiwan.no1.app.ssfm.features.search

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.util.SparseArray
import android.view.View
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.CALLBACK_SPARSE_INDEX_FOG_COLOR
import taiwan.no1.app.ssfm.misc.constants.Constant.CALLBACK_SPARSE_INDEX_IMAGE_URL
import taiwan.no1.app.ssfm.misc.constants.Constant.CALLBACK_SPARSE_INDEX_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.Constant.SPECIAL_NUMBER
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_FOG_COLOR
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_IMAGE_URL
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.FRAGMENT_SEARCH_HISTORY
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.FRAGMENT_SEARCH_INDEX
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.FRAGMENT_SEARCH_RESULT
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_HISTORY
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.usecases.SaveKeywordHistoryUsecase
import taiwan.no1.app.ssfm.models.usecases.SaveSearchHistoryCase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(private val context: Context, private val addHistoryUsecase: SaveSearchHistoryCase) :
    BaseViewModel() {
    /** For navigating to other fragments from the parent activity */
    lateinit var navigateListener: (fragmentTag: String, params: SparseArray<Any>?) -> Unit
    /** For pop the current fragment listener */
    lateinit var popFragment: (popToFragmentTag: String) -> Unit

    /** Menu Title */
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_search)) }
    /** The keyword of a song or singer's name */
    val keyword by lazy { ObservableField<String>("") }
    /** Check search view is clicked or un-clicked */
    val isSearching by lazy { ObservableBoolean() }
    val collapseView by lazy { ObservableBoolean() }

    private var pBkgImageUrl: String? = null
    private var pFgBlurColor: Int? = null

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
        collapseSearchView()
        popFragment(FRAGMENT_SEARCH_INDEX)
        return false
    }

    /**
     * The action of opening the search view.
     *
     * @param view
     */
    fun openSearchView(view: View?) {
        collapseSearchView(false)
        navigateListener(FRAGMENT_SEARCH_HISTORY, null)
    }

    /**
     * The action of submitting the search query.
     *
     * @param query the query of song's or singer's name for searching a music.
     */
    fun querySubmit(query: String): Boolean {
        query.takeIf(String::isNotBlank)?.let {
            navigateListener(FRAGMENT_SEARCH_RESULT, SparseArray<Any>().apply {
                put(CALLBACK_SPARSE_INDEX_KEYWORD, it)
                put(CALLBACK_SPARSE_INDEX_IMAGE_URL, pBkgImageUrl.orEmpty())
                put(CALLBACK_SPARSE_INDEX_FOG_COLOR, pFgBlurColor ?: SPECIAL_NUMBER)
            })
            lifecycleProvider.execute(addHistoryUsecase, SaveKeywordHistoryUsecase.RequestValue(it)) {
                // For ensuring that the search view focus is canceled.
                onComplete { keyword.set(it) }
            }
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
            popFragment(FRAGMENT_SEARCH_HISTORY)
        }
        else {
            // TODO(jieyi): 9/25/17 `debounce` the suggestion list.
            if (keyword.get() != newText) {
                navigateListener(FRAGMENT_SEARCH_HISTORY, null)
            }
        }

        return true
    }
    //endregion

    fun collapseSearchView(collapse: Boolean = true) {
        isSearching.set(!collapse)
        collapseView.set(collapse)
    }

    /**
     * The click event when clicking a item to the history list.
     *
     * @param params of the song or singer's name.
     *
     * @event_from [taiwan.no1.app.ssfm.features.search.RecyclerViewSearchHistoryViewModel.selectHistoryItem]
     * @event_from [taiwan.no1.app.ssfm.features.search.RecyclerViewSearchArtistChartViewModel.artistOnClick]
     * @event_from [taiwan.no1.app.ssfm.features.search.RecyclerViewSearchTrackChartViewModel.trackOnClick]
     */
    @Subscribe(tags = [Tag(VIEWMODEL_CLICK_HISTORY)])
    fun receiveClickHistoryEvent(params: HashMap<String, String>) {
        pBkgImageUrl = params[VIEWMODEL_PARAMS_IMAGE_URL]
        pFgBlurColor = params[VIEWMODEL_PARAMS_FOG_COLOR]?.toInt()
        querySubmit(params[VIEWMODEL_PARAMS_KEYWORD].orEmpty())
    }
}