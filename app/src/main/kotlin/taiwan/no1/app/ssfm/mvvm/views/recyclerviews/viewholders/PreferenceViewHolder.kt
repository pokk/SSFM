package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_selected
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_title
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, PreferenceEntity>(view) {
    override fun initView(model: PreferenceEntity, position: Int, adapter: Any) {
        adapter as ExpandRecyclerViewAdapter
        this.itemView.tv_title.text = model.title
        this.itemView.tv_selected.text = model.attributes
        this.itemView.onClick {
            val newPosition = adapter.calculateIndex(position)
            if (adapter.isCollapsed(newPosition)) {
                adapter.expand(position, newPosition)
            }
            else {
                adapter.collapse(position, newPosition)
            }
        }
    }
}