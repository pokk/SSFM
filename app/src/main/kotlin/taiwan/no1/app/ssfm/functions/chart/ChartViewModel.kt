package taiwan.no1.app.ssfm.functions.chart

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.hideSoftKeyboard
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_SIMILAR

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartViewModel(private val context: Context) :
    BaseViewModel() {
    /** Menu Title */
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_charts)) }
    /** The keyword of a song or singer's name */
    val keyword by lazy { ObservableField<String>("") }
    /** Check search view is clicked or un-clicked */
    val isSearching by lazy { ObservableBoolean() }
    val collapseView by lazy { ObservableBoolean() }

    //region Action from View
    /**
     * The action of closing the search view.
     */
    fun closeSearchView(): Boolean {
        collapseSearchView()
        return false
    }

    /**
     * The action of opening the search view.
     *
     * @hashCode view
     */
    fun openSearchView(view: View?) {
        collapseSearchView(false)
    }

    /**
     * The action of submitting the search query.
     *
     * @hashCode query the query of song's or singer's name for searching a music.
     *
     * @event_to [taiwan.no1.app.ssfm.functions.chart.ChartActivity.navigateToArtistDetail]
     */
    fun querySubmit(query: String): Boolean {
        // TODO(jieyi): 11/5/17
        // - Close the search bar.
        // - Dismiss the previous search fragment.
        RxBus.get().post(VIEWMODEL_CLICK_SIMILAR, query)
        context.hideSoftKeyboard()
        return true
    }

    /**
     * The action of the search query's newText is changed.
     *
     * @hashCode newText inputting by the user in the search view.
     */
    fun textChanged(newText: String): Boolean {
        return true
    }
    //endregion

    fun collapseSearchView(collapse: Boolean = true) {
        isSearching.set(!collapse)
    }
}