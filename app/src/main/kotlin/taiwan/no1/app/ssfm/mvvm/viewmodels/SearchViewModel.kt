package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(activity: Activity): BaseViewModel(activity) {
    /** Menu Title */
    val title = ObservableField<String>()
    /** Check search view is clicked or un-clicked */
    val isSearching = ObservableField<Boolean>()

    init {
        title.set(activity.getString(R.string.menu_search))
    }

    fun closeSearchView(): Boolean {
        isSearching.set(false)
        return false
    }

    fun openSearchView(view: View) {
        isSearching.set(true)
    }
}