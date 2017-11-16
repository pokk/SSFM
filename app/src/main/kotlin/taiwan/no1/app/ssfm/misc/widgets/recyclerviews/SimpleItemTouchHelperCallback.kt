package taiwan.no1.app.ssfm.misc.widgets.recyclerviews

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * @author  jieyi
 * @since   11/16/17
 */
class SimpleItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter,
                                    private val viewmodel: ItemTouchViewmodelCallback? = null) : ItemTouchHelper.Callback() {
    // Disable the long press drag function.
    override fun isLongPressDragEnabled(): Boolean = false

    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END

        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
//        viewmodel.onItemMove(recyclerView, viewHolder, target)
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewmodel?.onItemDismiss(viewHolder.adapterPosition, direction)
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }
}