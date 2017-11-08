package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters

import android.support.v7.util.DiffUtil
import com.devrapid.adaptiverecyclerview.AdaptiveAdapter
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import io.reactivex.Observable
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewtype.ExpandableViewTypeFactory
import taiwan.no1.app.ssfm.models.IExpandVisitable
import taiwan.no1.app.ssfm.models.entities.PreferenceEntity

/**
 * An adapter for expandable recycler view .
 *
 * @author  jieyi
 * @since   9/6/17
 */
class ExpandRecyclerViewAdapter(override var dataList: MutableList<IExpandVisitable>):
    AdaptiveAdapter<ExpandableViewTypeFactory, IExpandVisitable, AdaptiveViewHolder<ExpandableViewTypeFactory, IExpandVisitable>>() {
    override var typeFactory: ExpandableViewTypeFactory = ExpandableViewTypeFactory()
    /**
     *  This is for keeping the item position of the first layer. After a main item
     *  was expanded, the children quantity of the main item will be recorded to
     *  this list.
     *
     *  Example as the below:
     *
     *  INDEX       0   1   2   3   4   5   ...
     *  -------------------------------------------
     *  QUANTITY    0   0   3   0   3   0   ...
     *
     *  The main item with index 3 of new index position will be `6`.
     *  The item with index 4 of new index will be `7`.
     *  The item with index 5 of new index will be `11`.
     */
    private val originalParentPosition: MutableList<Int> = MutableList(dataList.size, { 0 })

    class ExpandDiffUtil(private var oldList: MutableList<IExpandVisitable>,
                         private var newList: MutableList<IExpandVisitable>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
    }

    /**
     * The action for expanding a main item.
     *
     * @param position The index of the [dataList]'s item which is clicked.
     * @param newIndex The index of the item which is clicked will be.
     */
    fun expand(position: Int, newIndex: Int) {
        updateList {
            val subList = dataList[newIndex].let {
                changeVisibleChildNumber(position, it.childItemList.size)
                it.isExpanded = true
                it.childItemList
            }
            ArrayList(dataList).toMutableList().apply { addAll(newIndex + 1, subList as Collection<IExpandVisitable>) }
        }
    }

    /**
     * The action for collapsing a main item.
     *
     * @param position The index of the [dataList]'s item which is clicked.
     * @param newIndex The index of the original item position.
     */
    fun collapse(position: Int, newIndex: Int) {
        updateList {
            val subList = dataList[newIndex].let {
                changeVisibleChildNumber(position, 0)
                it.isExpanded = false
                it.childItemList
            }
            ArrayList(dataList).toMutableList().apply { subList(newIndex + 1, newIndex + 1 + subList.size).clear() }
        }
    }

    /**
     * Calculating the new index of the clicked item.
     *
     * @param oldPos The original position of an item is clicked.
     * @return A new index after an item is clicked.
     */
    fun calculateIndex(oldPos: Int): Int = (0..(oldPos - 1)).sumBy { originalParentPosition[it] } + oldPos

    /**
     * Checking whether an item state is collapsed or not by checking the [originalParentPosition]. If
     * the value of the [dataList] is non-zero, that means the item is expanded.
     *
     * @param position The index in [dataList] of an item.
     * @return value is true → expanded; otherwise, collapsed.
     */
    fun isExpanded(position: Int): Boolean = dataList[position].isExpanded

    /**
     * Get the parent index of an expanded child item.
     *
     * @param childIndex The index of a child item was clicked.
     * @return The index of the parent.
     */
    fun findParentIndex(childIndex: Int): Int {
        (childIndex downTo 0).filter { dataList[it] is PreferenceEntity }.forEach { return it }

        // This is fail situation.
        return -1
    }

    /**
     * Connect a child item's [Observable] (the click event) to the parent's [Observer]. i.e. Let
     * click event be able to transmit to the parent.
     *
     * @param parentIndex The index of a child item's parent.
     * @param getChildObservable The click observable in a child item.
     */
    fun connectParentItem(parentIndex: Int, getChildObservable: Observable<String>) {
        getChildObservable.subscribe((dataList[parentIndex] as PreferenceEntity).observer!!)
    }

    /**
     * Refresh the [dataList].
     *
     * @param getNewListBlock
     */
    private fun updateList(getNewListBlock: () -> MutableList<IExpandVisitable>) {
        val newList = getNewListBlock()
        DiffUtil.calculateDiff(ExpandDiffUtil(dataList, newList)).dispatchUpdatesTo(this)
        dataList = newList
    }

    /**
     * Change the visible items of the children quantity on the [originalParentPosition].
     *
     * @param index The index of a main item.
     * @param size The main item's children quantity.
     */
    private fun changeVisibleChildNumber(index: Int, size: Int) {
        originalParentPosition[index] = size
    }
}