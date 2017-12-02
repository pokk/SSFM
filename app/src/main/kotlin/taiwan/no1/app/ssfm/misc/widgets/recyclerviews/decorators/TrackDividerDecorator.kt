package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.decorators

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import taiwan.no1.app.ssfm.R


/**
 * @author  jieyi
 * @since   2017/12/02
 */
class TrackDividerDecorator(context: Context, orientation: Int) : DividerItemDecoration(context, orientation) {
    private val divider: Drawable by lazy {
        context.resources.getDrawable(R.drawable.recyclerview_line_divider, context.theme)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + 300
        val right = parent.width - parent.paddingRight - 50

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight

            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
    }
}