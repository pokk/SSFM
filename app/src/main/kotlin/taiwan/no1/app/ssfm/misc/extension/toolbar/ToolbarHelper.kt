package taiwan.no1.app.ssfm.misc.extension.toolbar

import android.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import io.reactivex.rxkotlin.toSingle

/**
 * @author  jieyi
 * @since   11/17/17
 */
class ToolbarHelper(private val view: View) {
    fun getCurrentFragment(callback: (Fragment) -> Unit) {
        val toolbar = recursiveFindToolbar(view) ?: error("This view isn't in the toolbar. Please check it again.")

        val outerLayout = toolbar.parent as ViewGroup
        (0..outerLayout.childCount).
            first { outerLayout.getChildAt(it) is FrameLayout }.
            toSingle().map { outerLayout.getChildAt(it) as ViewGroup }.
            subscribe { container, t2 ->
            }
    }

    private fun recursiveFindToolbar(view: View): Toolbar? {
        var parent = view.parent

        while (null != parent) {
            if (parent is Toolbar) return parent
            parent = parent.parent
        }

        return null
    }
}