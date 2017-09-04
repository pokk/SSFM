package taiwan.no1.app.ssfm.mvvm.views.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.logi
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
abstract class BaseAdapter(var itemList: MutableList<ExpandableItem>): RecyclerView.Adapter<BaseViewHolder>() {
    private var recyclerViewList: MutableList<RecyclerView> = mutableListOf()

    class ExpandDiffUtil(private var oldList: MutableList<ExpandableItem>,
                         private var newList: MutableList<ExpandableItem>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = this.oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            this.oldList[oldItemPosition].hashCode() == this.newList[newItemPosition].hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
    }

    fun expand(position: Int) {
        logi(itemList[position].isExpandable, itemList[position])

        if (!itemList[position].isExpandable) {
            return
        }

        val subList = itemList[position].let {
            it.isExpandable = false
            it.childItemList
        }
        val newlist = ArrayList(itemList).toMutableList().apply {
            addAll(position + 1, subList as Collection<ExpandableItem>)
        }
        val res = DiffUtil.calculateDiff(ExpandDiffUtil(itemList, newlist))
        res.dispatchUpdatesTo(this)
        // FIXME(jieyi): 9/4/17 這邊的index出現不更新的狀態
        itemList = newlist
        logd(itemList)
    }

    override fun getItemCount(): Int = this.itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = View.inflate(parent.context, R.layout.item_preference_first_layer_title, null)

        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        itemList[position].let {
            holder.initView(it, position, this)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerViewList.add(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerViewList.remove(recyclerView)
    }
}