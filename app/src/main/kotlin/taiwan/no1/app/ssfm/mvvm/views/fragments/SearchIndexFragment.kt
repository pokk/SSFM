package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecyclerViewScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshRecyclerView
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchIndexViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewArtistChartViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewTrackChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchIndexFragment: AdvancedFragment<FragmentSearchIndexViewModel, FragmentSearchIndexBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchIndexViewModel
    @Inject lateinit var albumInfoUsecase: BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<ArtistEntity.Artist>()
    private var trackRes = mutableListOf<TrackEntity.Track>()

    //endregion

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, ArtistEntity.Artist>(R.layout.item_artist_type_1,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewArtistChartViewModel(item, albumInfoUsecase).apply {
                    onAttach(this@SearchIndexFragment)
                }
            }
            artistLoadmore = object: RecyclerViewScrollCallback {
                override fun loadMoreEvent(recyclerView: RecyclerView, total: Int) {
                    if (artistInfo.canLoadMoreFlag && !artistInfo.isLoading) {
                        artistInfo.isLoading = true
                        viewModel.fetchArtistList(artistInfo.page, artistInfo.limit, updateArtistCallback)
                    }
                }
            }
            artistDecoration = HorizontalItemDecorator(20)
            trackLayoutManager = WrapContentLinearLayoutManager(activity)
            trackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, TrackEntity.Track>(R.layout.item_music_type_1,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewTrackChartViewModel(item, albumInfoUsecase).apply {
                    onAttach(this@SearchIndexFragment)
                }
            }
            trackLoadmore = object: RecyclerViewScrollCallback {
                override fun loadMoreEvent(recyclerView: RecyclerView, total: Int) {
                    if (trackInfo.canLoadMoreFlag && !trackInfo.isLoading) {
                        trackInfo.isLoading = true
                        viewModel.fetchTrackList(trackInfo.page, trackInfo.limit, updateTrackCallback)
                    }
                }
            }
        }
        artistInfo.page.takeIf { 1 >= it && artistInfo.canLoadMoreFlag }?.let {
            viewModel.fetchArtistList(artistInfo.page, artistInfo.limit, updateArtistCallback)
        }
        trackInfo.page.takeIf { 1 >= it && trackInfo.canLoadMoreFlag }?.let {
            viewModel.fetchTrackList(trackInfo.page, trackInfo.limit, updateTrackCallback)
            // NEW
//            viewModel.fetchTrackList(trackInfo.page, trackInfo.limit) { list, total ->
//                callback(list, total, artistInfo)
//            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_index
    //endregion

    /**
     * The operation for updating the list result by the adapter. Including updating the original list
     * and the showing list on the recycler view.
     *
     * @param block the block operation for new data list.
     * @return a new updated list.
     */
    // HACK(jieyi): 10/13/17 Here should be a general function.
//    private fun MutableList<TrackEntity.Track>.refreshRecyclerView(block: ArrayList<TrackEntity.Track>.() -> Unit) {
//        trackRes = (this to binding?.trackAdapter as BaseDataBindingAdapter<ItemMusicType1Binding, TrackEntity.Track>).
//            refreshRecyclerView(block)
//    }

    // NEW
    private inline fun <reified T> MutableList<T>.refreshRecyclerView(noinline block: ArrayList<T>.() -> Unit) =
        (this to binding?.trackAdapter as BaseDataBindingAdapter<*, T>).refreshRecyclerView(block)


    private val updateArtistCallback = { resList: List<ArtistEntity.Artist>, total: Int ->
        artistRes = (artistRes to binding?.artistAdapter as BaseDataBindingAdapter<ItemArtistType1Binding, ArtistEntity.Artist>).
            refreshRecyclerView {
                artistInfo.page += 1
                addAll(resList)
            }
        artistInfo.isLoading = false
        // Raise the stopping loading more data flag for avoiding to load again.
        artistInfo.canLoadMoreFlag = (total > artistInfo.page * artistInfo.limit)
    }

    private val updateTrackCallback = { resList: List<TrackEntity.Track>, total: Int ->
        trackRes.refreshRecyclerView {
            trackInfo.page += 1
            addAll(resList)
        }
        trackInfo.isLoading = false
        // Raise the stopping loading more data flag for avoiding to load again.
        trackInfo.canLoadMoreFlag = (total > trackInfo.page * trackInfo.limit)
    }

//    private fun <T> callback(resList: Collection<T>, total: Int, info: DataInfo, ) {
//        trackRes.refreshRecyclerView {
//            info.page += 1
//            addAll(resList)
//        }
//        info.isLoading = false
//        // Raise the stopping loading more data flag for avoiding to load again.
//        info.canLoadMoreFlag = (total > info.page * info.limit)
//    }

    data class DataInfo(var page: Int = 1,
                        val limit: Int = 20,
                        var isLoading: Boolean = false,
                        var canLoadMoreFlag: Boolean = true)
}