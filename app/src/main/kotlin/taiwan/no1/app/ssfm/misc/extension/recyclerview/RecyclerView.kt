package taiwan.no1.app.ssfm.misc.extension.recyclerview

import android.support.v7.widget.RecyclerView
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemArtistType2Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType2Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType4Binding
import taiwan.no1.app.ssfm.databinding.ItemSearchHistoryType1Binding
import taiwan.no1.app.ssfm.databinding.ItemTagType1Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType1Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType2Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType3Binding
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   10/13/17
 */
typealias BaseAdapter<V> = BaseDataBindingAdapter<V, BaseEntity>
typealias ArtistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, BaseEntity>
typealias TrackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, BaseEntity>
typealias TagAdapter = BaseDataBindingAdapter<ItemTagType1Binding, BaseEntity>
typealias HistoryAdapter = BaseDataBindingAdapter<ItemSearchHistoryType1Binding, BaseEntity>
typealias SimilarArtistAdapter = BaseDataBindingAdapter<ItemArtistType2Binding, BaseEntity>
typealias ArtistTopTrackAdapter = BaseDataBindingAdapter<ItemMusicType2Binding, BaseEntity>
typealias AlbumTrackAdapter = BaseDataBindingAdapter<ItemMusicType4Binding, BaseEntity>
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
fun <T> Pair<BaseDataBindingAdapter<*, T>, MutableList<T>>.refreshRecyclerView(block: ArrayList<T>.() -> Unit) {
    val newList = first.refresh(second, ArrayList(second).apply(block)).toMutableList()

    // Add the new list into original list.
    second.addAll(newList)
}

/**
 * For updating the [DataInfo]'s information, refreshing the [RecyclerView]'s list, adding the new
 * data list into temp list for keeping the data.
 * Also controlling the fetching timer for avoiding that fetching a lots times in the same time.
 *
 * @param resList the data of fetching from remote data base.
 * @param total the quantity of the data.
 * @param adapter [RecyclerView]'s adapter.
 * @param info the [DataInfo] information of the current data where we fetched.
 */
fun <T> MutableList<T>.refreshAndChangeList(resList: Collection<T>,
                                            total: Int,
                                            adapter: BaseDataBindingAdapter<*, T>,
                                            info: DataInfo) {
    (adapter to this).refreshRecyclerView {
        info.page += 1
        addAll(resList)
    }
    info.isLoading = false
    // Raise the stopping loading more data flag for avoiding to load again.
    info.canLoadMoreFlag = (total > info.page * info.limit)
}

fun DataInfo.firstFetch(fetchBlock: (info: DataInfo) -> Unit) =
    page.takeIf { 1 >= it && canLoadMoreFlag }?.let { fetchBlock(this) }

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
                dataList.refreshAndChangeList(resList, total, adapter, dataInfo)
            }
        }
    }
}