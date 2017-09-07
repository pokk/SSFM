package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import kotlinx.android.synthetic.main.item_preference_second_layer_title.view.tv_selection
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/5/17
 */
class DepartmentViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, Department>(view) {
    override fun initView(model: Department, position: Int, adapter: Any) {
        this.itemView.tv_selection.text = "This is second layer : $position"
    }
}