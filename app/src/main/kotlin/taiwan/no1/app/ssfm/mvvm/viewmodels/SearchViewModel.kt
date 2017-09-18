package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import android.widget.SearchView
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(activity: Activity): BaseViewModel(activity) {
    val search = ObservableField<SearchView>()
    val title = ObservableField<String>()

    init {
        title.set(activity.getString(R.string.menu_search))
    }

    fun closeSearchView(view: View) {
        logw("closeSearchView")
    }

    fun openSearchView(view: View) {
        logw("openSearchView")
    }
}