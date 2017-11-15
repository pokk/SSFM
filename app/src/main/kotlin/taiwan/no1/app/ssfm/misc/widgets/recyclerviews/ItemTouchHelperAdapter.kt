package taiwan.no1.app.ssfm.misc.widgets.recyclerviews

/**
 * @author  jieyi
 * @since   11/16/17
 */
interface ItemTouchHelperAdapter {
    fun onItemDismiss(position: Int)
    fun onItemMove(fromPosition: Int, toPosition: Int)
}