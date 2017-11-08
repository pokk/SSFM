package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewholders.BindingHolder

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class BaseDataBindingAdapter<BH: ViewDataBinding, D>(@LayoutRes private val layoutId: Int,
                                                     private var dataList: MutableList<D>,
                                                     private val bindVHBlock: (holder: BindingHolder<BH>, item: D) -> Unit):
    RecyclerView.Adapter<BindingHolder<BH>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<BH> =
        DataBindingUtil.inflate<BH>(LayoutInflater.from(parent.context), layoutId, parent, false).
            let { BindingHolder(it) }

    override fun onBindViewHolder(holder: BindingHolder<BH>, position: Int) = bindVHBlock(holder, dataList[position])

    override fun getItemCount(): Int = dataList.size

    // TODO(jieyi): 9/28/17 Add footer layout!

    fun refresh(oldData: List<D>,
                newData: List<D>,
                areContentsTheSame: (old: D, new: D) -> Boolean = { _, _ -> false }): List<D> {
        DiffUtil.calculateDiff(object: DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldData.size
            override fun getNewListSize(): Int = newData.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldData[oldItemPosition]?.hashCode() == newData[newItemPosition]?.hashCode()

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition])
        }).dispatchUpdatesTo(this)

        // We need to bind DiffUtil and adapter then notify the data change.
        dataList = newData.toMutableList()

        return newData
    }
}