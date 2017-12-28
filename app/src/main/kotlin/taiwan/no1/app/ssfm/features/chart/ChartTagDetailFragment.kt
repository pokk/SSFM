package taiwan.no1.app.ssfm.features.chart

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.HorizontalItemDecorator
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.VerticalItemDecorator
import org.jetbrains.anko.act
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailTagBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TagTopAlbumAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TagTopArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TagTopTrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.keepAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.extension.recyclerview.restoreAllLastItemPosition
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.decorators.TrackDividerDecorator
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import javax.inject.Inject
import javax.inject.Named

/**
 * @author  jieyi
 * @since   10/26/17
 */
class ChartTagDetailFragment : AdvancedFragment<ChartTagDetailFragmentViewModel, FragmentDetailTagBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_TAG: String = "param_music_tag"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(tag: String = "") = ChartTagDetailFragment().apply {
            arguments = bundleOf(ARG_PARAM_TAG to tag)
        }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartTagDetailFragmentViewModel
    @Inject lateinit var searchMusicCase: SearchMusicV2Case
    @field:[Inject Named("add_playlist_item")] lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val albumInfo by lazy { DataInfo() }
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var albumRes = mutableListOf<BaseEntity>()
    private var artistRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()
    private var nestViewLastPosition = 0
    // Get the arguments from the bundle here.
    private val musicTag: String by lazy { this.arguments.getString(ARG_PARAM_TAG) }

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        binding?.apply {
            listOf(Pair(albumInfo, albumLayoutManager),
                Pair(artistInfo, artistLayoutManager),
                Pair(trackInfo, trackLayoutManager)).restoreAllLastItemPosition()
            nsvContainer.scrollTo(0, nestViewLastPosition)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onPause() {
        super.onPause()
        binding?.apply {
            listOf(Triple(albumInfo, rvTopAlbum, albumLayoutManager),
                Triple(artistInfo, rvTopArtists, artistLayoutManager),
                Triple(trackInfo, rvTopTrack, trackLayoutManager)).keepAllLastItemPosition()
            nestViewLastPosition = nsvContainer.computeVerticalScrollOffset()
        }
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            albumLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            trackLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

            albumAdapter = TagTopAlbumAdapter(R.layout.item_album_type_2, albumRes) { holder, item ->
                holder.binding.avm = RecyclerViewTagTopAlbumViewModel(item).apply {
                    onAttach(this@ChartTagDetailFragment)
                }
            }
            artistAdapter = TagTopArtistAdapter(R.layout.item_artist_type_3, artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewTagTopArtistViewModel(item).apply {
                    onAttach(this@ChartTagDetailFragment)
                }
            }
            trackAdapter = TagTopTrackAdapter(R.layout.item_music_type_7, trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewTagTopTrackViewModel(searchMusicCase,
                    addPlaylistItemCase,
                    item).apply {
                    onAttach(this@ChartTagDetailFragment)
                    clickEvent = { (activity as ChartActivity).openBottomSheet(item) }
                }
            }

            albumLoadmore = RVCustomScrollCallback(binding?.albumAdapter as TagTopAlbumAdapter, albumInfo,
                albumRes) { page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit ->
                viewModel.fetchHotAlbum(musicTag, page, limit, callback)
            }
            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as TagTopArtistAdapter, artistInfo,
                artistRes) { page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit ->
                viewModel.fetchHotArtist(musicTag, page, limit, callback)
            }
            trackLoadmore = RVCustomScrollCallback(binding?.trackAdapter as TagTopTrackAdapter, trackInfo,
                trackRes) { page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit ->
                viewModel.fetchHotTrack(musicTag, page, limit, callback)
            }

            albumDecoration = HorizontalItemDecorator(20)
            artistDecoration = HorizontalItemDecorator(20)
            trackDecoration = VerticalItemDecorator(40)
            trackDecoration = TrackDividerDecorator(act, LinearLayout.VERTICAL)
        }
        // First time showing this fragment.
        viewModel.fetchTagDetailInfo(musicTag)
        albumInfo.firstFetch {
            viewModel.fetchHotAlbum(musicTag, it.page, it.limit) { resList, total ->
                albumRes.refreshAndChangeList(resList, total, binding?.albumAdapter as TagTopAlbumAdapter, it)
            }
        }
        artistInfo.firstFetch {
            viewModel.fetchHotArtist(musicTag, it.page, it.limit) { resList, total ->
                artistRes.refreshAndChangeList(resList, total, binding?.artistAdapter as TagTopArtistAdapter, it)
            }
        }
        trackInfo.firstFetch {
            viewModel.fetchHotTrack(musicTag, it.page, it.limit) { resList, total ->
                trackRes.refreshAndChangeList(resList, total, binding?.trackAdapter as TagTopTrackAdapter, it)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_tag
//endregion
}