package taiwan.no1.app.ssfm.pattern.strategy

import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.trello.rxlifecycle2.components.RxFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.BaseAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecyclerViewScrollCallback
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewholders.BindingHolder
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   10/27/17
 */
class InitialHorizontalRVStrategy<V: ViewDataBinding, A: BaseAdapter<V>>(private val layoutManager: ((layoutManager: RecyclerView.LayoutManager) -> Unit)?,
                                                                         private val adapter: ((adapter: BaseAdapter<V>) -> Unit)?,
                                                                         private val callback: ((callback: RecyclerViewScrollCallback) -> Unit)?,
                                                                         private val decoration: ((decoration: RecyclerView.ItemDecoration) -> Unit)?,
                                                                         private val rxFragment: RxFragment,
                                                                         private val adapter1: () -> BaseDataBindingAdapter<V, BaseEntity>?,
                                                                         private val adapter2: RecyclerView.Adapter<*>?,
                                                                         private val dataInfo: DataInfo,
                                                                         private val entity: MutableList<BaseEntity>,
                                                                         private val fetchFun: (page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit) -> Unit,
                                                                         private val bindingBlock: (holder: BindingHolder<ViewDataBinding>, item: BaseEntity) -> Unit,
                                                                         private val viewId: Int):
    IInitialRecyclerViewStrategy {
    override fun initLayoutManager() {
        layoutManager?.invoke(WrapContentLinearLayoutManager(rxFragment.activity,
            LinearLayoutManager.HORIZONTAL,
            false))
    }

    override fun initAdapter() {
        // FIXME(jieyi): 10/27/17 This is very difficult to extract to a common function. :((
        adapter?.invoke(BaseDataBindingAdapter(viewId, entity, bindingBlock))
    }

    override fun initDecoration() {
        decoration?.invoke(HorizontalItemDecorator(20))
    }

    override fun initLoadMore() {
        callback?.invoke(RVCustomScrollCallback(adapter1 as BaseDataBindingAdapter<V, BaseEntity>,
            dataInfo,
            entity,
            fetchFun))
    }
}