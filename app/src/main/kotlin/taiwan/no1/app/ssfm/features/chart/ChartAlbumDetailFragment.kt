package taiwan.no1.app.ssfm.features.chart

import android.os.Bundle
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailAlbumBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.AlbumTrackAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
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
    private var trackRes = mutableListOf<BaseEntity>()
    // Get the arguments from the bundle here.
    private val artistAlbumName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_ALBUM_NAME) }
    private val artistName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_NAME) }

    //region Fragment lifecycle
    override fun onDestroy() {
        (binding?.trackAdapter as BaseDataBindingAdapter<*, *>).detachAll()
        super.onDestroy()
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
            viewModel.fetchDetailInfo(artistAlbumName, artistName) {
                it.track?.tracks?.let {
                    trackRes.refreshAndChangeList(it, 0, binding?.trackAdapter as AlbumTrackAdapter, info)
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_album
    //endregion
}