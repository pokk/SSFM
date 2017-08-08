package taiwan.no1.app.ssfm.mvvm.view.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Implement the [IViewTypeFactory], we design that getting a view type is equal get a layout id. The reason
 * is convenient to set the view.
 *
 * @author  Jieyi
 * @since   1/7/17
 */

class ViewTypeFactory: IViewTypeFactory {
    override fun createViewHolder(type: Int, itemView: View): RecyclerView.ViewHolder = when (type) {
        else -> throw error("Illegal type")
    }
}
