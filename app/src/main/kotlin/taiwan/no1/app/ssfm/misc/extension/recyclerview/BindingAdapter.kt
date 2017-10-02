package taiwan.no1.app.ssfm.misc.extension.recyclerview

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * @author  jieyi
 * @since   10/1/17
 */
@BindingAdapter("android:layoutManager", "android:adapter")
fun RecyclerView.setAdapter(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}

// OPTIMIZE(jieyi): 10/2/17 Help want! I might instead an anonymous variable method for loading more.
interface RecyclerViewScrollCallback {
    fun loadMoreEvent(recyclerView: RecyclerView, total: Int)
}

@BindingAdapter("android:loadMore")
fun RecyclerView.setOnScrollListener(callback: RecyclerViewScrollCallback?) =
    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            (recyclerView.layoutManager as LinearLayoutManager).let {
                val visibleItems = it.childCount
                val totalItems = it.itemCount
                val pastVisibleItems = it.findFirstVisibleItemPosition()
                if (visibleItems + pastVisibleItems >= totalItems) {
                    callback?.loadMoreEvent(recyclerView, totalItems)
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        }
    })
