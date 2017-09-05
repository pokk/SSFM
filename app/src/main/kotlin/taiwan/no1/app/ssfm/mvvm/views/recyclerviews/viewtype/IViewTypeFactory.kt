package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype

import android.support.v7.widget.RecyclerView
import android.view.View
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Department

/**
 * A factory interface of the view type.
 *
 * @author  jieyi
 * @since   6/27/17
 */
interface IViewTypeFactory {
    // XXX(jieyi): 9/5/17 Add the new type here.
    fun type(company: Company): Int

    fun type(department: Department): Int

    /**
     * Creating a view holder.
     *
     * @param type a res ID of layout.
     * @param itemView a view after inflating.
     * @return [BaseViewHolder] of recycler view holder.
     */
    fun createViewHolder(type: Int, itemView: View): RecyclerView.ViewHolder
}