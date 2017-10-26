package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartTagViewModel(val item: TagEntity.Tag): BaseViewModel() {
    val tagName by lazy { ObservableField<String>(item.name?.apply { this[0].toUpperCase() }) }
    var clickItemListener: ((item: TagEntity.Tag) -> Unit)? = null

    fun tagOnClick(view: View) {
        clickItemListener?.invoke(item)
    }
}