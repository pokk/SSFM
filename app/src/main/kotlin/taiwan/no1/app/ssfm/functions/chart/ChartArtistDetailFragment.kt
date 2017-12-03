package taiwan.no1.app.ssfm.functions.chart

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.HorizontalItemDecorator
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager
import kotlinx.android.synthetic.main.fragment_detail_artist.rv_album
import org.jetbrains.anko.act
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailArtistBinding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistTopTrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.SimilarArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.keepAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.extension.recyclerview.restoreAllLastItemPosition
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class ChartArtistDetailFragment : AdvancedFragment<ChartArtistDetailFragmentViewModel, FragmentDetailArtistBinding>() {
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
        fun newInstance(mbid: String = "",
                        artistName: String = "") = ChartArtistDetailFragment().also {
            it.arguments = Bundle().apply {
                putString(ARG_PARAM_MBID, mbid)
                putString(ARG_PARAM_ARTIST_NAME, artistName)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartArtistDetailFragmentViewModel
    @Inject lateinit var searchMusicCase: SearchMusicV2Case
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()
    private var nestViewLastPosition = 0
    // Get the arguments from the bundle here.
    private val mbid: String by lazy { this.arguments.getString(ARG_PARAM_MBID) }
    private val artistName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_NAME) }

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        binding?.apply {
            listOf(Pair(artistInfo, artistLayoutManager),
                Pair(trackInfo, trackLayoutManager)).restoreAllLastItemPosition()
            nsvContainer.scrollTo(0, nestViewLastPosition)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onPause() {
        super.onPause()
        binding?.apply {
            listOf(Triple(artistInfo, rvSimilar, artistLayoutManager),
                Triple(trackInfo, rvTopTracks, trackLayoutManager)).keepAllLastItemPosition()
            nestViewLastPosition = nsvContainer.computeVerticalScrollOffset()
        }
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = SimilarArtistAdapter(R.layout.item_artist_type_2, artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartSimilarArtistViewModel(item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                    clickItemListener = {}
                }
            }
            artistDecoration = HorizontalItemDecorator(20)

            trackLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            trackAdapter = ArtistTopTrackAdapter(R.layout.item_music_type_2, trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartArtistHotTrackViewModel(searchMusicCase, item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                }
            }
        }
        // FIXME(jieyi): 2017/12/03 Change to MVVM binding.
        val horizontalInfiniteCycleViewPager = rv_album as HorizontalInfiniteCycleViewPager
        // First time showing this fragment.
        artistInfo.firstFetch { info ->
            viewModel.fetchDetailInfo(mbid, artistName) {
                it.artist?.similar?.artists?.let {
                    artistRes.refreshAndChangeList(it, 0, binding?.artistAdapter as SimilarArtistAdapter, info)
                    // If the artist exists then we can find the artist's detail tracks and albums.
                    viewModel.fetchHotTracks(artistName) {
                        trackRes.refreshAndChangeList(it, 0, binding?.trackAdapter as ArtistTopTrackAdapter, trackInfo)
                    }
                    viewModel.fetchHotAlbum(artistName) {
                        horizontalInfiniteCycleViewPager.adapter = HorizontalPagerAdapter(act,
                            it.map { it.images?.get(EXTRA_LARGE)?.text.orEmpty() })
                    }
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_artist
    //endregion
}