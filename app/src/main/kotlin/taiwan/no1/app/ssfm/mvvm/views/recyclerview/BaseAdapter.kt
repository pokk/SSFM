package taiwan.no1.app.ssfm.mvvm.views.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
abstract class BaseAdapter(val itemList: List<Any>): RecyclerView.Adapter<BaseViewHolder>() {
    private var recyclerViewList: MutableList<RecyclerView> = mutableListOf()

    override fun getItemCount(): Int = this.itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = View.inflate(parent.context, R.layout.item_preference_first_layer_title, null)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        itemList[position].let {
            if (it is ExpandableItem) {
                holder.initView(it, position, this)
            }
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