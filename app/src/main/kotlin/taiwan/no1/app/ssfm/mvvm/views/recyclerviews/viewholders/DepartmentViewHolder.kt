package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import kotlinx.android.synthetic.main.item_preference_second_layer_title.view.tv_selection
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.BaseAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.BaseViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/5/17
 */
class DepartmentViewHolder(view: View): BaseViewHolder<Department>(view) {
    override fun initView(model: Department, position: Int, adapter: BaseAdapter) {
        super.initView(model, position, adapter)

        this.itemView.tv_selection.text = "This is second layer : $position"
    }
}