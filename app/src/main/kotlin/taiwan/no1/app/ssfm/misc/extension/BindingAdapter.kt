package taiwan.no1.app.ssfm.misc.extension

import android.databinding.BindingAdapter
import android.widget.SearchView

/**
 * @author  jieyi
 * @since   10/1/17
 */
@BindingAdapter("android:searchText")
fun SearchView.setSearchViewText(keyword: String) =
    keyword.takeIf { it.isNotBlank() }?.let { this.setQuery(it, false) }
