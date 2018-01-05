package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.ItemTouchHelperAdapter
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewholders.BindingHolder

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
open class BaseDataBindingAdapter<BH : ViewDataBinding, D>(private val lifecycleProvider: LifecycleProvider<*>,
                                                           @LayoutRes private val layoutId: Int,
                                                           private var dataList: MutableList<D>,
                                                           private val bindVHBlock: (holder: BindingHolder<BH>, item: D, position: Int) -> Unit) :
    RecyclerView.Adapter<BindingHolder<BH>>(), ItemTouchHelperAdapter {
    // For releasing the viewmodel.
    private var viewmodels = mutableListOf<BaseViewModel>()
    private lateinit var bindingHolder: BindingHolder<BH>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<BH>(LayoutInflater.from(parent.context), layoutId, parent, false).
            let { BindingHolder(it).apply { bindingHolder = this } }

    override fun onBindViewHolder(holder: BindingHolder<BH>, position: Int) {
        bindVHBlock(holder, dataList[position], position)
        addToViewmodelKeeper(holder)
    }

    override fun onViewDetachedFromWindow(holder: BindingHolder<BH>) {
        removeFromViewmodelKeeper(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
    }

    override fun onItemDismiss(position: Int) {
        removeFromViewmodelKeeper(bindingHolder)
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun detachAll() {
        viewmodels.run {
            forEach(BaseViewModel::onDetach)
            clear()
        }
    }

    fun refresh(oldData: List<D>,
                newData: List<D>,
                areContentsTheSame: (old: D, new: D) -> Boolean = { _, _ -> false }): List<D> {
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
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

    private fun addToViewmodelKeeper(holder: BindingHolder<BH>) =
        (holder.binding.javaClass.getMethod("getAvm").invoke(bindingHolder.binding) as BaseViewModel).
            takeIf { it !in viewmodels }?.
            let {
                it.onAttach(lifecycleProvider)
                viewmodels.add(it)
            } ?: false

    private fun removeFromViewmodelKeeper(holder: BindingHolder<BH>) =
        (holder.binding.javaClass.getMethod("getAvm").invoke(bindingHolder.binding) as BaseViewModel).let {
            if (viewmodels.remove(it)) it.onDetach()
        }
}