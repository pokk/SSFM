package taiwan.no1.app.ssfm.functions.search

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.HorizontalItemDecorator
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType1Binding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.gContext
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.keepAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.extension.recyclerview.restoreAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.scaledDrawable
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchIndexFragment : AdvancedFragment<SearchIndexFragmentViewModel, FragmentSearchIndexBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the
         * provided parameters.
         *
         * @return A new instance of [android.app.Fragment] SearchIndexFragment.
         */
        fun newInstance() = SearchIndexFragment()
    }
    //endregion

    @Inject override lateinit var viewModel: SearchIndexFragmentViewModel
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        binding?.apply {
            listOf(Pair(artistInfo, artistLayoutManager),
                Pair(trackInfo, trackLayoutManager)).restoreAllLastItemPosition()
        }
    }

    override fun onPause() {
        super.onPause()
        binding?.apply {
            listOf(Triple(artistInfo, rvTopArtists, artistLayoutManager),
                Triple(trackInfo, rvTopTracks, trackLayoutManager)).keepAllLastItemPosition()
        }
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity,
                LinearLayoutManager.HORIZONTAL,
                false)
            trackLayoutManager = WrapContentLinearLayoutManager(activity)

            artistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, BaseEntity>(R.layout.item_artist_type_1,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewSearchArtistChartViewModel(item).apply {
                    onAttach(this@SearchIndexFragment)
                }
                val sd = gContext().scaledDrawable(R.drawable.ic_pause_circle, 0.5f, 0.5f)
                holder.binding.tvPlayCount.setCompoundDrawables(sd, null, null, null)
            }
            trackAdapter = BaseDataBindingAdapter<ItemMusicType1Binding, BaseEntity>(R.layout.item_music_type_1,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewSearchTrackChartViewModel(item).apply {
                    onAttach(this@SearchIndexFragment)
                }
            }

            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as ArtistAdapter,
                artistInfo, artistRes, viewModel::fetchArtistList)
            trackLoadmore = RVCustomScrollCallback(binding?.trackAdapter as TrackAdapter, trackInfo,
                trackRes, viewModel::fetchTrackList)

            artistDecoration = HorizontalItemDecorator(20)
        }
        // First time showing this fragment.
        artistInfo.firstFetch {
            viewModel.fetchArtistList(it.page, it.limit) { resList, total ->
                artistRes.refreshAndChangeList(resList, total, binding?.artistAdapter as ArtistAdapter, it)
            }
        }
        trackInfo.firstFetch {
            viewModel.fetchTrackList(it.page, it.limit) { resList, total ->
                trackRes.refreshAndChangeList(resList, total, binding?.trackAdapter as TrackAdapter, it)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_index
    //endregion
}