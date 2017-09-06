package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import com.devrapid.kotlinknifer.logw
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_title
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.ExpandAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/5/17
 */
class CompanyViewHolder(view: View): AdaptiveViewHolder<ExpandableViewTypeFactory, IExpandVisitable, ExpandAdapter>(view) {
    override fun initView(model: IExpandVisitable, position: Int, adapter: ExpandAdapter) {
        this.itemView.tv_title.text = "position: $position"
        this.itemView.onClick {
            val newPosition = adapter.calculateIndex(position)
            logw(position, newPosition)
            if (adapter.isCollapsed(newPosition)) {
                adapter.expand(position, newPosition)
            }
            else {
                adapter.collapse(position, newPosition)
            }
        }
    }
}