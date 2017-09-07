package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_title
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandRecyclerViewAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/5/17
 */
class CompanyViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, Company>(view) {
    override fun initView(model: Company, position: Int, adapter: Any) {
        adapter as ExpandRecyclerViewAdapter
        this.itemView.tv_title.text = "position: $position"
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