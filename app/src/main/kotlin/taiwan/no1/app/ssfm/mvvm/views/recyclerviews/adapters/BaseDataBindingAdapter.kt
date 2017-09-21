package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.BindingHolder

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class BaseDataBindingAdapter<BH: ViewDataBinding, D>(@LayoutRes val layout: Int,
                                                     private val bindVHBlock: (holder: BindingHolder<BH>, position: Int) -> Unit):
    RecyclerView.Adapter<BindingHolder<BH>>() {

    var dataSize = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<BH> =
        DataBindingUtil.inflate<BH>(LayoutInflater.from(parent.context),
            R.layout.item_search_music_type_1, parent, false).let { BindingHolder(it) }

    override fun onBindViewHolder(holder: BindingHolder<BH>, position: Int) = bindVHBlock(holder, position)

    override fun getItemCount(): Int = dataSize

    fun refresh(oldData: List<D>,
                newData: List<D>,
                areContentsTheSame: (D, D) -> Boolean = { _, _ -> false }): List<D> {
        DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldData.size
            override fun getNewListSize(): Int = newData.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition] == newData[newItemPosition]

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition])
        }).dispatchUpdatesTo(this)

        // We need to bind DiffUtil and adapter then notify the data change.
        dataSize = newData.size

        return newData
    }
}