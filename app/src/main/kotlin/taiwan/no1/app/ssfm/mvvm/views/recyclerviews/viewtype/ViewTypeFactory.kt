package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.View
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.CompanyViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.DepartmentViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ViewTypeFactory.TypeResource.COMPANY
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ViewTypeFactory.TypeResource.DEPARTMENT

/**
 * Implement the [IViewTypeFactory], we design that getting a view type is equal get a layout id. The reason
 * is convenient to set the view.
 *
 * @author  Jieyi
 * @since   1/7/17
 */

class ViewTypeFactory: IViewTypeFactory {
    enum class TypeResource(@LayoutRes val id: Int) {
        COMPANY(R.layout.item_preference_first_layer_title),
        DEPARTMENT(R.layout.item_preference_second_layer_title)
    }

    override fun type(company: Company): Int = COMPANY.ordinal

    override fun type(department: Department): Int = DEPARTMENT.ordinal

    override fun createViewHolder(type: Int, itemView: View): RecyclerView.ViewHolder = when (type) {
        COMPANY.ordinal -> CompanyViewHolder(
            itemView)
        DEPARTMENT.ordinal -> DepartmentViewHolder(
            itemView)
        else -> throw error("Illegal type")
    }
}
