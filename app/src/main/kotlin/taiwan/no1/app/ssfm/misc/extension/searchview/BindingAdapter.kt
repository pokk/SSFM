package taiwan.no1.app.ssfm.misc.extension.searchview

import android.databinding.BindingAdapter
import android.widget.SearchView

/**
 * @author  jieyi
 * @since   10/1/17
 */
@BindingAdapter("android:searchText")
fun SearchView.setSearchViewText(keyword: String) =
    keyword.takeIf { it.isNotBlank() }?.let { this.setQuery(it, false) }

@BindingAdapter("android:collapsed")
fun SearchView.collapsedView(setCollapsed: Boolean) {
    if (setCollapsed)
    // FIXME(jieyi): 10/4/17 Couldn't find again.
        this.onActionViewCollapsed()
}