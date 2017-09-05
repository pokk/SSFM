package taiwan.no1.app.ssfm.mvvm.views.recyclerviews

import android.content.Context
import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View
import taiwan.no1.app.ssfm.mvvm.models.IVisitable

/**
 * An abstract view holder.
 *
 * @author  jieyi
 * @since   9/5/17
 */
abstract class BaseViewHolder<in M: IVisitable>(view: View): RecyclerView.ViewHolder(view) {
    protected val mContext: Context = view.context

    /**
     * Set the views' properties.
     * NOTE: In Kotlin, can't use generic in constructor generic so you must cast the [model] to a type what you want
     * in the beginning.
     *ß
     * @param model a data model after input from a list.
     * @param position the index of a list.
     * @param adapter parent adapter.
     */
    @CallSuper
    open fun initView(model: M, position: Int, adapter: BaseAdapter) {
    }
}