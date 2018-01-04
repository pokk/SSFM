package taiwan.no1.app.ssfm.features.chart

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.cleveroad.fanlayoutmanager.FanLayoutManager
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.HorizontalItemDecorator
import org.jetbrains.anko.act
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.ctx
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailArtistBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_ARTIST_NAME
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistTopAlbumAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistTopTrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.SimilarArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.keepAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.extension.recyclerview.restoreAllLastItemPosition
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import javax.inject.Inject
import javax.inject.Named

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
        fun newInstance(mbid: String = "", artistName: String = "") =
            ChartArtistDetailFragment().apply {
                arguments = bundleOf(ARG_PARAM_MBID to mbid,
                                     ARG_PARAM_ARTIST_NAME to artistName)
            }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartArtistDetailFragmentViewModel
    @Inject lateinit var searchMusicCase: SearchMusicV2Case
    @field:[Inject Named("add_playlist_item")] lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private val albumInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()
    private var albumRes = mutableListOf<BaseEntity>()
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
            artistAdapter = SimilarArtistAdapter(R.layout.item_artist_type_2, artistRes) { holder, item, index ->
                holder.binding.avm = RecyclerViewChartSimilarArtistViewModel(item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                    clickItemListener = {}
                }
            }
            artistDecoration = HorizontalItemDecorator(20)

            trackLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            trackAdapter = ArtistTopTrackAdapter(R.layout.item_music_type_2, trackRes) { holder, item, index ->
                holder.binding.avm =
                    RecyclerViewChartArtistHotTrackViewModel(searchMusicCase, addPlaylistItemCase, item).apply {
                        onAttach(this@ChartArtistDetailFragment)
                        clickEvent = { (activity as ChartActivity).openBottomSheet(item) }
                    }
            }

            albumLayoutManager = FanLayoutManager(act, FanLayoutManagerSettings.newBuilder(ctx).apply {
                withFanRadius(true)
                withAngleItemBounce(5f)
                withViewHeightDp(190f)
                withViewWidthDp(150f)
            }.build())
            albumAdapter = ArtistTopAlbumAdapter(R.layout.item_album_type_1, albumRes) { holder, item, index ->
                holder.binding.avm = RecyclerViewChartArtistHotAlbumViewModel(item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                    clickItemListener = {
                        (albumLayoutManager as FanLayoutManager).run {
                            when {
                                isItemSelected && selectedItemPosition == it.index -> {
                                    val params = hashMapOf(
                                        VIEWMODEL_PARAMS_ARTIST_NAME to it.artist?.name.orEmpty(),
                                        VIEWMODEL_PARAMS_ARTIST_ALBUM_NAME to it.name.orEmpty())
                                    (act as ChartActivity).navigateToAlbumDetail(params)
                                }
                                else -> {
                                    deselectItem()
                                    switchItem(rvAlbum, it.index)
                                }
                            }
                        }
                    }
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
                    viewModel.fetchHotAlbum(artistName) {
                        albumRes.refreshAndChangeList(it, 0, binding?.albumAdapter as ArtistTopAlbumAdapter, albumInfo)
                    }
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_artist
    //endregion
}