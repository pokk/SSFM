package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import kotlinx.android.synthetic.main.item_preference_first_layer_toggle.view.st_switch
import kotlinx.android.synthetic.main.item_preference_first_layer_toggle.view.tv_title
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceToggleEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceToggleViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, PreferenceToggleEntity>(view) {
    override fun initView(model: PreferenceToggleEntity, position: Int, adapter: Any) {
        adapter as ExpandRecyclerViewAdapter
        this.itemView.tv_title.text = model.title
        this.itemView.st_switch.isChecked = model.isToggle
    }
}