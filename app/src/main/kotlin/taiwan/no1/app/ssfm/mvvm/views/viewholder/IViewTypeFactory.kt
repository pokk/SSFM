package taiwan.no1.app.ssfm.mvvm.views.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * A factory interface of the view type.
 *
 * @author  jieyi
 * @since   6/27/17
 */
interface IViewTypeFactory {

    /**
     * Creating a view holder.
     *
     * @param type a res ID of layout.
     * @param itemView a view after inflating.
     * @return [BaseViewHolder] of recycler view holder.
     */
    fun createViewHolder(type: Int, itemView: View): RecyclerView.ViewHolder
}