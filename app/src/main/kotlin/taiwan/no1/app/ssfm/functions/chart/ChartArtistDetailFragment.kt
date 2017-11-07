package taiwan.no1.app.ssfm.functions.chart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailArtistBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType2Binding
import taiwan.no1.app.ssfm.databinding.ItemMusicType2Binding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistTopTrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.SimilarArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartArtistDetailViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class ChartArtistDetailFragment: AdvancedFragment<FragmentChartArtistDetailViewModel, FragmentDetailArtistBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_MBID: String = "param_mbid"
        private const val ARG_PARAM_ARTIST_NAME: String = "param_artist_name"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(mbid: String = "", artistName: String = "") = ChartArtistDetailFragment().also {
            it.arguments = Bundle().apply {
                putString(ARG_PARAM_MBID, mbid)
                putString(ARG_PARAM_ARTIST_NAME, artistName)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: FragmentChartArtistDetailViewModel
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()
    // Get the arguments from the bundle here.
    private val mbid: String by lazy { this.arguments.getString(ARG_PARAM_MBID) }
    private val artistName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_NAME) }

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = BaseDataBindingAdapter<ItemArtistType2Binding, BaseEntity>(R.layout.item_artist_type_2,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartSimilarArtistViewModel(item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                    clickItemListener = {}
                }
            }
            artistDecoration = HorizontalItemDecorator(20)

            trackLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            trackAdapter = BaseDataBindingAdapter<ItemMusicType2Binding, BaseEntity>(R.layout.item_music_type_2,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartArtistHotTrackViewModel(item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                }
            }
        }
        // First time showing this fragment.
        artistInfo.firstFetch { info ->
            viewModel.fetchDetailInfo(mbid, artistName) {
                it.artist?.similar?.artists?.let {
                    artistRes.refreshAndChangeList(it, 0, binding?.artistAdapter as SimilarArtistAdapter, info)
                    // If the artist exists then we can find the artist's detail tracks and albums.
                    viewModel.fetchHotTracks(artistName) {
                        trackRes.refreshAndChangeList(it, 0, binding?.trackAdapter as ArtistTopTrackAdapter, trackInfo)
                    }
                    viewModel.fetchHotAlbum(artistName)
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_artist
    //endregion
}