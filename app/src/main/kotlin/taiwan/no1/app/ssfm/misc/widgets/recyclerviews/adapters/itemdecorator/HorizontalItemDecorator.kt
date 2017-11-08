package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.itemdecorator

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *
 * @author  Jieyi
 * @since   12/30/16
 */

class HorizontalItemDecorator(private val space: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position: Int = parent.getChildAdapterPosition(view)
        val childrenCount: Int = parent.childCount

        if (0 == position)
            outRect.set(space, space, space / 2, space)
        else if (childrenCount - 1 != position)
            outRect.set(space / 2, space, space, space)
        else
            outRect.set(space / 2, space, space / 2, space)
    }
}