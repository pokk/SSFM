package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import de.umass.lastfm.Artist
import de.umass.lastfm.Track
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshRecyclerView
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchIndexViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewTrackChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchIndexFragment: AdvancedFragment<FragmentSearchIndexViewModel, FragmentSearchIndexBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchIndexViewModel
    private var trackRes = mutableListOf<Track>()
    private var ArtistRes = mutableListOf<Artist>()

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity)
            trackLayoutManager = WrapContentLinearLayoutManager(activity)
            artistAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, Artist>(R.layout.item_music_type_1,
                ArtistRes) { holder, item ->
                //                holder.binding.avm = RecyclerViewTrackChartViewModel(item, activity)
            }
            trackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, Track>(R.layout.item_music_type_1,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewTrackChartViewModel(item, activity)
            }
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
    private fun MutableList<Track>.refreshRecyclerView(block: ArrayList<Track>.() -> Unit) {
        trackRes = (this to binding?.trackAdapter as BaseDataBindingAdapter<ItemMusicType1Binding, Track>).
            refreshRecyclerView(block)
    }
}