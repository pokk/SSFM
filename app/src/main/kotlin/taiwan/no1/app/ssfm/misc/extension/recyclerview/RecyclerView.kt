package taiwan.no1.app.ssfm.misc.extension.recyclerview

import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout.HORIZONTAL
import taiwan.no1.app.ssfm.databinding.ItemAlbumType1Binding
import taiwan.no1.app.ssfm.databinding.ItemAlbumType2Binding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemArtistType2Binding
import taiwan.no1.app.ssfm.databinding.ItemArtistType3Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType2Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType3Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType4Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType5Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType6Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType7Binding
import taiwan.no1.app.ssfm.databinding.ItemPlaylistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemPlaylistType2Binding
import taiwan.no1.app.ssfm.databinding.ItemRankType1Binding
import taiwan.no1.app.ssfm.databinding.ItemSearchHistoryType1Binding
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.databinding.ItemTagType1Binding
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseMultipleTypeDataBindingAdapter
import taiwan.no1.app.ssfm.models.IExpandVisitable
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   10/13/17
 */
typealias BaseAdapter<V> = BaseDataBindingAdapter<V, BaseEntity>
typealias RankAdapter = BaseDataBindingAdapter<ItemRankType1Binding, BaseEntity>
typealias ArtistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, BaseEntity>
typealias TrackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, BaseEntity>
typealias TagAdapter = BaseDataBindingAdapter<ItemTagType1Binding, BaseEntity>
typealias HistoryAdapter = BaseDataBindingAdapter<ItemSearchHistoryType1Binding, BaseEntity>
typealias SimilarArtistAdapter = BaseDataBindingAdapter<ItemArtistType2Binding, BaseEntity>
typealias ArtistTopTrackAdapter = BaseDataBindingAdapter<ItemMusicType2Binding, BaseEntity>
typealias ArtistTopAlbumAdapter = BaseDataBindingAdapter<ItemAlbumType1Binding, BaseEntity>
typealias AlbumTrackAdapter = BaseDataBindingAdapter<ItemMusicType4Binding, BaseEntity>
typealias TagTopAlbumAdapter = BaseDataBindingAdapter<ItemAlbumType2Binding, BaseEntity>
typealias TagTopArtistAdapter = BaseDataBindingAdapter<ItemArtistType3Binding, BaseEntity>
typealias TagTopTrackAdapter = BaseDataBindingAdapter<ItemMusicType7Binding, BaseEntity>
typealias RankChartDetailAdapter = BaseDataBindingAdapter<ItemMusicType6Binding, BaseEntity>
typealias PlaylistItemAdapter = BaseDataBindingAdapter<ItemMusicType5Binding, BaseEntity>
typealias PlaylistAdapter = BaseDataBindingAdapter<ItemPlaylistType1Binding, BaseEntity>
typealias RecentlyAdapter = BaseDataBindingAdapter<ItemMusicType3Binding, BaseEntity>
typealias SearchHistoryAdapter = BaseDataBindingAdapter<ItemSearchMusicType1Binding, BaseEntity>
typealias MultipleTypeAdapter = BaseMultipleTypeDataBindingAdapter<ViewDataBinding, IExpandVisitable>
typealias DFPlaylistAdapter = BaseDataBindingAdapter<ItemPlaylistType2Binding, BaseEntity>

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
                    var lastPosition: Int = 0,
                    var lastOffset: Int = 0,
                    var isLoading: Boolean = false,
                    var canLoadMoreFlag: Boolean = true)

/**
 * For keeping the last item position when the [Activity] or [Fragment] is going to pause state.
 *
 * @param recyclerview the item in the [RecyclerView].
 * @param layoutManager the [recyclerview]'s [RecyclerView.LayoutManager].
 */
fun DataInfo.keepLastItemPosition(recyclerview: RecyclerView,
                                  layoutManager: LinearLayoutManager?) = apply {
    lastOffset = if (HORIZONTAL == layoutManager?.orientation)
        recyclerview.computeHorizontalScrollOffset()
    else
        recyclerview.computeVerticalScrollOffset()
    lastPosition = layoutManager?.findFirstVisibleItemPosition() ?: 0
}

/**
 * For restoring the last item position when the [Activity] or [Fragment] resumes.
 *
 * @param layoutManager the recyclerview's [RecyclerView.LayoutManager].
 */
fun DataInfo.restoreLastItemPosition(layoutManager: LinearLayoutManager?) =
    takeIf { 0 <= lastPosition }?.let { layoutManager?.scrollToPositionWithOffset(0, -lastOffset) }

fun List<Pair<DataInfo, RecyclerView.LayoutManager?>>.restoreAllLastItemPosition() =
    forEach { it.first.restoreLastItemPosition(it.second as LinearLayoutManager) }

fun List<Triple<DataInfo, RecyclerView, RecyclerView.LayoutManager?>>.keepAllLastItemPosition() =
    forEach { it.first.keepLastItemPosition(it.second, it.third as LinearLayoutManager) }

class RVCustomScrollCallback<T>(private val adapter: BaseDataBindingAdapter<*, T>,
                                private val dataInfo: DataInfo,
                                private var dataList: MutableList<T>,
                                private val fetchMethod: (page: Int, limit: Int, callback: (List<T>, total: Int) -> Unit) -> Unit) :
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