package taiwan.no1.app.ssfm.misc.utilies

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Recycler
import android.support.v7.widget.RecyclerView.State
import android.util.AttributeSet
import com.devrapid.kotlinknifer.loge

/**
 * Wrapper the linear layout manager for catching the exceptions and don't make app crash.
 *
 * @author  jieyi
 * @since   9/23/17
 */
class WrapContentLinearLayoutManager: LinearLayoutManager {
    constructor(context: Context): super(context)
    constructor(context: Context, orientation: Int, reverseLayout: Boolean): super(context, orientation, reverseLayout)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int):
        super(context, attrs, defStyleAttr, defStyleRes)

    // FIXED(jieyi): 9/23/17 Workaround for fixing the android original recycler view problem.
    override fun onLayoutChildren(recycler: Recycler?, state: State?) {
        try {
            super.onLayoutChildren(recycler, state)
        }
        catch (e: Exception) {
            loge(e)
        }
    }
}