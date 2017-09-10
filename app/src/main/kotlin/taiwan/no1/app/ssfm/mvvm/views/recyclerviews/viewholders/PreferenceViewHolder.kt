package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.kotlinknifer.observer
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.iv_title_icon
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_selected
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_title
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * A [AdaptiveViewHolder] for controlling the main preference items with an option selection.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, PreferenceEntity>(view) {
    override fun initView(model: PreferenceEntity, position: Int, adapter: Any) {
        adapter as ExpandRecyclerViewAdapter
        val newPosition = adapter.calculateIndex(position)

        // Create an observer for the click event of children.
        if (null == model.observer) {
            model.observer = observer {
                this.itemView.tv_selected.text = it
                adapter.collapse(position, newPosition)
                // TODO(jieyi): 9/10/17 Changing app theme processing.
            }
        }

        this.itemView.onClick {
            if (adapter.isCollapsed(newPosition)) {
                adapter.expand(position, newPosition)
            }
            else {
                adapter.collapse(position, newPosition)
            }
        }

        // Initial the items.
        if (model.icon > 0) {
            this.itemView.iv_title_icon.setImageDrawable(this.mContext.getDrawable(model.icon))
        }
        this.itemView.tv_title.text = model.title
        this.itemView.tv_selected.text = model.attributes
    }
}