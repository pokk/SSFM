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

@BindingAdapter("android:loadMore", "android:onScrollState")
fun RecyclerView.setOnScrollListener(loadMore: (recyclerview: RecyclerView, total: Int) -> Unit,
                                     onScrollStateChangedEvent: (recyclerview: RecyclerView, newState: Int) -> Unit) =
    addOnScrollListener(object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            (recyclerView.layoutManager as LinearLayoutManager).let {
                val visibleItems = it.childCount
                val totalItems = it.itemCount
                val pastVisibleItems = it.findFirstVisibleItemPosition()
                if (visibleItems + pastVisibleItems >= totalItems) {
                    loadMore(recyclerView, totalItems)
                }
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            onScrollStateChangedEvent(recyclerView, newState)
        }
    })