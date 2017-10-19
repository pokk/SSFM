package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.resCallback
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
            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as ArtistAdapter, artistInfo,
                artistRes, viewModel::fetchArtistList)
            artistDecoration = HorizontalItemDecorator(20)
            trackLayoutManager = WrapContentLinearLayoutManager(activity)
            trackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, TrackEntity.Track>(R.layout.item_music_type_1,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewTrackChartViewModel(item, albumInfoUsecase).apply {
                    onAttach(this@SearchIndexFragment)
                }
            }
            trackLoadmore = RVCustomScrollCallback(binding?.trackAdapter as TrackAdapter, trackInfo,
                trackRes, viewModel::fetchTrackList)
        }
        // First time showing this fragment.
        artistInfo.page.takeIf { 1 >= it && artistInfo.canLoadMoreFlag }?.let {
            viewModel.fetchArtistList(artistInfo.page, artistInfo.limit) { resList, total ->
                artistRes = resCallback(resList, total, artistRes, binding?.artistAdapter as ArtistAdapter, artistInfo)
            }
        }
        trackInfo.page.takeIf { 1 >= it && trackInfo.canLoadMoreFlag }?.let {
            viewModel.fetchTrackList(trackInfo.page, trackInfo.limit) { resList, total ->
                trackRes = resCallback(resList, total, trackRes, binding?.trackAdapter as TrackAdapter, trackInfo)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_index
    //endregion
}