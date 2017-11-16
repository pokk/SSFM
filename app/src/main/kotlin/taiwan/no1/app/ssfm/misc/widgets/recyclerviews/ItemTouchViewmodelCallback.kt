package taiwan.no1.app.ssfm.misc.widgets.recyclerviews

/**
 * @author  jieyi
 * @since   11/16/17
 */
interface ItemTouchViewmodelCallback {
    fun onItemDismiss(position: Int, direction: Int)
//    fun onItemMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)
}