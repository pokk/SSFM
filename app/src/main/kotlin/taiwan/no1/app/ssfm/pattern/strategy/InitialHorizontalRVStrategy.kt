package taiwan.no1.app.ssfm.pattern.strategy

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.trello.rxlifecycle2.components.RxFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.BaseAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecyclerViewScrollCallback
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator

/**
 * @author  jieyi
 * @since   10/27/17
 */
class InitialHorizontalRVStrategy<A: BaseAdapter>(private val layoutManager: ((layoutManager: RecyclerView.LayoutManager) -> Unit)?,
                                                  private val adapter: ((adapter: A) -> Unit)?,
                                                  private val callback: ((callback: RecyclerViewScrollCallback) -> Unit)?,
                                                  private val decoration: ((decoration: RecyclerView.ItemDecoration) -> Unit)?,
                                                  private val rxFragment: RxFragment,
                                                  private val adapter2: RecyclerView.Adapter<*>,
                                                  private val dataInfo: DataInfo,
                                                  private val entity: MutableList<BaseEntity>):
    IInitialRecyclerViewStrategy {
    override fun initLayoutManager() {
        layoutManager?.invoke(WrapContentLinearLayoutManager(rxFragment.activity,
            LinearLayoutManager.HORIZONTAL,
            false))
    }

    override fun initAdapter() {
        // FIXME(jieyi): 10/27/17 This is very difficult to extract to a common function. :((
//        adapter?.invoke(BaseDataBindingAdapter<ItemUniversalType2Binding, BaseEntity>(R.layout.item_universal_type_2,
//            entity) { holder, item ->
//            holder.binding.avm = RecyclerViewUniversal2ViewModel(item).apply { onAttach(rxFragment) }
//        })
    }

    override fun initDecoration() {
        decoration?.invoke(HorizontalItemDecorator(20))
    }

    override fun initLoadMore(fetchFun: (page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit) -> Unit) {
        callback?.invoke(RVCustomScrollCallback(adapter2 as A, dataInfo, entity, fetchFun))
    }
}