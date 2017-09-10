package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.internal.operators.observable.ObservableJust
import kotlinx.android.synthetic.main.item_preference_second_layer_title.view.tv_selection
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceOptionEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceOptionViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, PreferenceOptionEntity>(view) {
    override fun initView(model: PreferenceOptionEntity, position: Int, adapter: Any) {
        adapter as ExpandRecyclerViewAdapter

        this.itemView.tv_selection.text = model.title
        adapter.connectParentItem(adapter.findParentIndex(position),
            this.itemView.clicks().flatMap { ObservableJust<String>(model.title) })
    }
}