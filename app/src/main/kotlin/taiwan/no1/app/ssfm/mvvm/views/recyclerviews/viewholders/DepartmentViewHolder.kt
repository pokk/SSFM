package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import kotlinx.android.synthetic.main.item_preference_second_layer_title.view.tv_selection
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/5/17
 */
class DepartmentViewHolder(view: View):
    AdaptiveViewHolder<ExpandableViewTypeFactory, IExpandVisitable, ExpandAdapter>(view) {
    override fun initView(model: IExpandVisitable, position: Int, adapter: ExpandAdapter) {
        this.itemView.tv_selection.text = "This is second layer : $position"
    }
}