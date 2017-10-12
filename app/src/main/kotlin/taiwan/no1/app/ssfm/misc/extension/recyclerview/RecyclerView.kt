package taiwan.no1.app.ssfm.misc.extension.recyclerview

import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter

/**
 * @author  jieyi
 * @since   10/13/17
 */
fun <T> Pair<MutableList<T>, BaseDataBindingAdapter<*, T>>.refreshRecyclerView(block: ArrayList<T>.() -> Unit) =
    second.refresh(first, ArrayList(first).apply(block)).toMutableList()