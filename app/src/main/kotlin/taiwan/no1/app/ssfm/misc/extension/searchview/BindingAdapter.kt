package taiwan.no1.app.ssfm.misc.extension.searchview

import android.databinding.BindingAdapter
import android.widget.SearchView

/**
 * @author  jieyi
 * @since   10/1/17
 */
@BindingAdapter("android:searchText")
fun SearchView.setSearchViewText(keyword: String) =
    keyword.takeIf { it.isNotBlank() }?.let {
        setQuery(it, false)
        clearFocus()
    }

@BindingAdapter("android:collapseView")
fun SearchView.collapsedSearchView(collapse: Boolean) =
    if (collapse) onActionViewCollapsed() else Unit