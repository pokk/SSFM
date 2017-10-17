package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
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
    private var artistRes = mutableListOf<ArtistEntity.Artist>()
    private var trackRes = mutableListOf<TrackEntity.Track>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        trackRes.clear()
        artistRes.clear()
    }
    //endregion

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            trackLayoutManager = WrapContentLinearLayoutManager(activity)
            artistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, ArtistEntity.Artist>(R.layout.item_artist_type_1,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewArtistChartViewModel(item, albumInfoUsecase).apply {
                    onAttach(this@SearchIndexFragment)
                }
            }
            trackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, TrackEntity.Track>(R.layout.item_music_type_1,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewTrackChartViewModel(item, albumInfoUsecase).apply {
                    onAttach(this@SearchIndexFragment)
                }
            }
            artistDecoration = HorizontalItemDecorator(20)
        }
        viewModel.fetchArtistList {
            artistRes = (artistRes to binding?.artistAdapter as BaseDataBindingAdapter<ItemArtistType1Binding, ArtistEntity.Artist>).
                refreshRecyclerView { addAll(it) }
        }
        viewModel.fetchTrackList { trackRes.refreshRecyclerView { addAll(it) } }
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
    private fun MutableList<TrackEntity.Track>.refreshRecyclerView(block: ArrayList<TrackEntity.Track>.() -> Unit) {
        trackRes = (this to binding?.trackAdapter as BaseDataBindingAdapter<ItemMusicType1Binding, TrackEntity.Track>).
            refreshRecyclerView(block)
    }
}