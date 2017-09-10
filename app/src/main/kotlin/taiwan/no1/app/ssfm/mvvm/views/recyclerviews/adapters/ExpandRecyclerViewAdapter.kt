package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters

import android.support.v7.util.DiffUtil
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * An adapter for expandable recycler view .
 *
 * @author  jieyi
 * @since   9/6/17
 */
class ExpandRecyclerViewAdapter(override var dataList: MutableList<IExpandVisitable>):
    AdaptiveAdapter<ExpandableViewTypeFactory, IExpandVisitable, AdaptiveViewHolder<ExpandableViewTypeFactory, IExpandVisitable>>() {
    override var typeFactory: ExpandableViewTypeFactory = ExpandableViewTypeFactory()
    private val originalParentPosition: MutableList<Int> = MutableList(this.dataList.size, { 0 })

    class ExpandDiffUtil(private var oldList: MutableList<IExpandVisitable>,
                         private var newList: MutableList<IExpandVisitable>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = this.oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            this.oldList[oldItemPosition].hashCode() == this.newList[newItemPosition].hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
    }

    fun expand(position: Int, newIndex: Int) {
        this.updateList {
            val subList = this.dataList[newIndex].let {
                this.changeVisibleChildNumber(position, it.childItemList.size)
                it.isExpandable = false
                it.childItemList
            }
            ArrayList(dataList).toMutableList().apply { addAll(newIndex + 1, subList as Collection<IExpandVisitable>) }
        }
    }

    fun collapse(position: Int, newIndex: Int) {
        this.updateList {
            val subList = this.dataList[newIndex].let {
                this.changeVisibleChildNumber(position, 0)
                it.isExpandable = true
                it.childItemList
            }
            ArrayList(dataList).toMutableList().apply { subList(newIndex + 1, newIndex + 1 + subList.size).clear() }
        }
    }

    fun calculateIndex(oldPos: Int): Int = (0..(oldPos - 1)).sumBy { this.originalParentPosition[it] } + oldPos

    fun isCollapsed(position: Int): Boolean = this.dataList[position].isExpandable

    fun findParentIndex(childIndex: Int): Int {
        (childIndex downTo 0).filter { this.dataList[it] is PreferenceEntity }.forEach { return it }

        // This is fail situation.
        return -1
    }

    fun connectParentItem(parentIndex: Int, getChildObservable: Observable<String>) {
        getChildObservable.subscribe((this.dataList[parentIndex] as PreferenceEntity).observer!!)
    }

    private fun updateList(getNewListBlock: () -> MutableList<IExpandVisitable>) {
        val newList = getNewListBlock()
        DiffUtil.calculateDiff(ExpandDiffUtil(this.dataList, newList)).dispatchUpdatesTo(this)
        this.dataList = newList
    }

    private fun changeVisibleChildNumber(index: Int, size: Int) {
        this.originalParentPosition[index] = size
    }
}