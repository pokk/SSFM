package taiwan.no1.app.ssfm.features.chart

import android.os.Bundle
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import com.devrapid.kotlinknifer.toInstance
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailAlbumBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.HELPER_ADD_TO_PLAYLIST
import taiwan.no1.app.ssfm.misc.extension.recyclerview.AlbumTrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.entities.transforms.tToPlaylist
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import javax.inject.Inject
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class ChartAlbumDetailFragment : AdvancedFragment<ChartAlbumDetailFragmentViewModel, FragmentDetailAlbumBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_ARTIST_ALBUM_NAME: String = "param_artist_album_name"
        private const val ARG_PARAM_ARTIST_NAME: String = "param_artist_name"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(artistAlbumName: String = "", artistName: String = "") =
            ChartAlbumDetailFragment().apply {
                arguments = bundleOf(ARG_PARAM_ARTIST_ALBUM_NAME to artistAlbumName,
                                     ARG_PARAM_ARTIST_NAME to artistName)
            }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartAlbumDetailFragmentViewModel
    @Inject lateinit var fetchMusicCase: SearchMusicV2Case
    @field:[Inject Named("add_playlist_item")] lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val tagInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var tagRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<PlaylistItemEntity>()
    // Get the arguments from the bundle here.
    private val artistAlbumName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_ALBUM_NAME) }
    private val artistName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_NAME) }

    //region Fragment lifecycle
    override fun onDestroyView() {
        (binding?.trackAdapter as BaseDataBindingAdapter<*, *>).detachAll()
        super.onDestroyView()
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            trackLayoutManager = WrapContentLinearLayoutManager(activity)
            trackAdapter = AlbumTrackAdapter(this@ChartAlbumDetailFragment,
                                             R.layout.item_music_type_4,
                                             trackRes) { holder, item, index ->
                if (null == holder.binding.avm)
                    holder.binding.avm = RecyclerViewChartAlbumTrackViewModel(fetchMusicCase,
                                                                              addPlaylistItemCase,
                                                                              item,
                                                                              index).apply {
                        clickEvent = { (activity as ChartActivity).openBottomSheet(item) }
                    }
                else
                    holder.binding.avm?.setTrackItem(item, index)
            }
        }
        trackInfo.firstFetch { info ->
            viewModel.fetchDetailInfo(artistAlbumName, artistName) { album ->
                album.track?.tracks?.toInstance<TrackEntity.BaseTrack>()?.tToPlaylist()?.subscribe { tracks ->
                    trackRes.refreshAndChangeList(tracks, 0, binding?.trackAdapter as AlbumTrackAdapter, info)
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_album
    //endregion

    @Subscribe(tags = [(Tag(HELPER_ADD_TO_PLAYLIST))])
    fun addToPlaylist(trackUri: String) {
        playerHelper.also {
            if (it.isFirstTimePlayHere) {
                it.clearList()
                it.playInObject = this.javaClass.name
                // TODO(jieyi): 2018/01/17 We can't get the track url so we need to search once then get the real url.
//                it.addList(trackRes.map { (it as TrackEntity.Track).realUrl.orEmpty() })
                it.setCurrentIndex(trackUri)
            }
        }
    }
}