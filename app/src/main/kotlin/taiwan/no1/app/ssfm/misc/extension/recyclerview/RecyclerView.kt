package taiwan.no1.app.ssfm.misc.extension.recyclerview

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemArtistType2Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.databinding.ItemSearchHistoryType1Binding
import taiwan.no1.app.ssfm.databinding.ItemTagType1Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType1Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType2Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType3Binding
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter

/**
 * @author  jieyi
 * @since   10/13/17
 */
typealias BaseAdapter = BaseDataBindingAdapter<ViewDataBinding, BaseEntity>
typealias ArtistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, BaseEntity>
typealias TrackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, BaseEntity>
typealias TagAdapter = BaseDataBindingAdapter<ItemTagType1Binding, BaseEntity>
typealias HistoryAdapter = BaseDataBindingAdapter<ItemSearchHistoryType1Binding, BaseEntity>
typealias SimilarArtistAdapter = BaseDataBindingAdapter<ItemArtistType2Binding, BaseEntity>
typealias Universal1Adapter = BaseDataBindingAdapter<ItemUniversalType1Binding, BaseEntity>
typealias Universal2Adapter = BaseDataBindingAdapter<ItemUniversalType2Binding, BaseEntity>
typealias Universal3Adapter = BaseDataBindingAdapter<ItemUniversalType3Binding, BaseEntity>

/**
 * The operation for updating the list result by the adapter. Including updating the original list
 * and the showing list on the recycler view.
 *
 * @param block the block operation for new data list.
 * @return a new updated list.
 */
fun <T> Pair<BaseDataBindingAdapter<*, T>, MutableList<T>>.refreshRecyclerView(block: ArrayList<T>.() -> Unit) =
    first.refresh(second, ArrayList(second).apply(block)).toMutableList()

fun <T> resCallback(resList: Collection<T>,
                    total: Int,
                    list: MutableList<T>,
                    adapter: BaseDataBindingAdapter<*, T>,
                    info: DataInfo): MutableList<T> {
    val newestList = (adapter to list).refreshRecyclerView {
        info.page += 1
        addAll(resList)
    }
    info.isLoading = false
    // Raise the stopping loading more data flag for avoiding to load again.
    info.canLoadMoreFlag = (total > info.page * info.limit)

    return newestList
}

data class DataInfo(var page: Int = 1,
                    val limit: Int = 20,
                    var isLoading: Boolean = false,
                    var canLoadMoreFlag: Boolean = true)

class RVCustomScrollCallback<T>(private val adapter: BaseDataBindingAdapter<*, T>,
                                private val dataInfo: DataInfo,
                                private var dataList: MutableList<T>,
                                private val fetchMethod: (page: Int, limit: Int, callback: (List<T>, total: Int) -> Unit) -> Unit):
    RecyclerViewScrollCallback {
    override fun loadMoreEvent(recyclerView: RecyclerView, total: Int) {
        if (dataInfo.canLoadMoreFlag && !dataInfo.isLoading) {
            dataInfo.isLoading = true
            fetchMethod(dataInfo.page, dataInfo.limit) { resList, total ->
                dataList = resCallback(resList, total, dataList, adapter, dataInfo)
            }
        }
    }
}