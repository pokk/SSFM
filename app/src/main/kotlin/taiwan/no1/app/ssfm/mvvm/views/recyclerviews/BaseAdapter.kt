package taiwan.no1.app.ssfm.mvvm.views.recyclerviews

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import taiwan.no1.app.ssfm.mvvm.models.IVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.IViewTypeFactory
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ViewTypeFactory

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
abstract class BaseAdapter(var models: MutableList<IVisitable>): RecyclerView.Adapter<BaseViewHolder<IVisitable>>() {
    private var typeFactory: IViewTypeFactory = ViewTypeFactory()
    private val originalParentposition: MutableList<Int> = MutableList(this.models.size, { 0 })

    class ExpandDiffUtil(private var oldList: MutableList<IVisitable>,
                         private var newList: MutableList<IVisitable>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = this.oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            this.oldList[oldItemPosition].hashCode() == this.newList[newItemPosition].hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
    }

    fun expand(position: Int, newIndexPosition: Int) {
        val subList = models[newIndexPosition].let {
            it.isExpandable = false
            this.originalParentposition[newIndexPosition] = it.childItemList.size
            it.childItemList
        }
        val newList = ArrayList(models).toMutableList().apply {
            addAll(position + 1, subList as Collection<IVisitable>)
        }
        val res = DiffUtil.calculateDiff(ExpandDiffUtil(models, newList))
        res.dispatchUpdatesTo(this)
        models = newList
    }

    fun collapse(position: Int, newIndexPosition: Int) {
        val subList = models[newIndexPosition].let {
            it.isExpandable = true
            this.originalParentposition[newIndexPosition] = 0
            it.childItemList
        }
        val newList = ArrayList(models).toMutableList().apply {
            subList(newIndexPosition + 1, position + 1 + subList.size).clear()
        }
        val res = DiffUtil.calculateDiff(ExpandDiffUtil(models, newList))
        res.dispatchUpdatesTo(this)
        models = newList
    }

    fun calculateIndex(oldPos: Int): Int = (0..(oldPos - 1)).sumBy { this.originalParentposition[it] } + oldPos

    fun isCollapsed(position: Int): Boolean = this.models[position].isExpandable

    override fun getItemCount(): Int = this.models.size

    override fun onBindViewHolder(holder: BaseViewHolder<IVisitable>, position: Int) =
        holder.initView(this.models[position], position, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<IVisitable> {
        val itemView: View = View.inflate(parent.context, ViewTypeFactory.TypeResource.values()[viewType].id, null)

        return this.typeFactory.createViewHolder(viewType, itemView) as BaseViewHolder<IVisitable>
    }

    override fun getItemViewType(position: Int): Int = this.models[position].type(this.typeFactory)
}
