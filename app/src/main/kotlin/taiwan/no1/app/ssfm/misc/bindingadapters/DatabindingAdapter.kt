package taiwan.no1.app.ssfm.misc.bindingadapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.SearchView
import com.devrapid.kotlinknifer.logw

/**
 *
 * @author  jieyi
 * @since   10/1/17
 */
object DatabindingAdapter {
    @BindingAdapter("android:searchKeyword")
    fun SearchView.setSearchViewText(keyword: String) {
        logw("?????????????????", this, keyword)
    }

    @BindingAdapter("android:imgUrl")
    fun ImageView.setImage(url: String) {
        logw("??????????????????????????????", url)
    }
}
