package taiwan.no1.app.ssfm.mvvm.views.recyclerviews

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.devrapid.kotlinknifer.logd
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

    class ExpandDiffUtil(private var oldList: MutableList<IVisitable>,
                         private var newList: MutableList<IVisitable>): DiffUtil.Callback() {
        override fun getOldListSize(): Int = this.oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            this.oldList[oldItemPosition].hashCode() == this.newList[newItemPosition].hashCode()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = true
    }

    fun expand(position: Int) {
        if (!models[position].isExpandable) {
            return
        }

        val subList = models[position].let {
            it.isExpandable = false
            it.childItemList
        }
        val newlist = ArrayList(models).toMutableList().apply {
            addAll(position + 1, subList as Collection<IVisitable>)
        }
        val res = DiffUtil.calculateDiff(ExpandDiffUtil(models, newlist))
        res.dispatchUpdatesTo(this)
        // FIXME(jieyi): 9/4/17 這邊的index出現不更新的狀態
        models = newlist
        logd(models)
    }

    override fun getItemCount(): Int = this.models.size

    override fun onBindViewHolder(holder: BaseViewHolder<IVisitable>, position: Int) =
        holder.initView(this.models[position], position, this)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<IVisitable> {
        val itemView: View = View.inflate(parent.context, ViewTypeFactory.TypeResource.values()[viewType].id, null)

        return this.typeFactory.createViewHolder(viewType, itemView) as BaseViewHolder<IVisitable>
    }

    override fun getItemViewType(position: Int): Int = this.models[position].type(this.typeFactory)
}
