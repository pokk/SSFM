package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.iv_title_icon
import kotlinx.android.synthetic.main.item_preference_first_layer_toggle.view.st_switch
import kotlinx.android.synthetic.main.item_preference_first_layer_toggle.view.tv_title
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewtype.ExpandableViewTypeFactory
import taiwan.no1.app.ssfm.models.entities.PreferenceToggleEntity

/**
 * A [AdaptiveViewHolder] for controlling the main preference items with toggle button.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceToggleViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, PreferenceToggleEntity>(view) {
    override fun initView(model: PreferenceToggleEntity, position: Int, adapter: Any) {
        adapter as ExpandRecyclerViewAdapter

        // Initial the items.
        if (model.icon > 0) {
            itemView.iv_title_icon.setImageDrawable(mContext.getDrawable(model.icon))
        }
        itemView.tv_title.text = model.title
        itemView.st_switch.isChecked = model.isToggle
        itemView.st_switch.onCheckedChange { btn, isChecked ->
            // TODO(jieyi): 9/10/17 Do something about this toggle's reaction.
        }
    }
}