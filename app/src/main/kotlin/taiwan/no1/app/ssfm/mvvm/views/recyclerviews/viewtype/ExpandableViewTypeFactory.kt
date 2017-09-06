package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import com.devrapid.adaptiverecyclerview.ViewTypeFactory
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.CompanyViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.DepartmentViewHolder

/**
 *
 *
 * @author  jieyi
 * @since   9/6/17
 */
class ExpandableViewTypeFactory: ViewTypeFactory() {
    override var transformMap: MutableMap<Int, Pair<Int, (View) -> ViewHolder>> =
        mutableMapOf(1 to Pair(R.layout.item_preference_first_layer_title, { view -> CompanyViewHolder(view) }),
            2 to Pair(R.layout.item_preference_first_layer_title, { view -> DepartmentViewHolder(view) }))

    // XXX(jieyi): 9/5/17 Add the new type here.
    fun type(company: Company): Int = 1

    fun type(department: Department): Int = 2
}