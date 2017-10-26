package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailTagBinding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartTagDetailViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import javax.inject.Inject

/**
 * @author  jieyi
 * @since   10/26/17
 */
class ChartTagDetailFragment: AdvancedFragment<FragmentChartTagDetailViewModel, FragmentDetailTagBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_TAG: String = "param_music_tag"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(tag: String = "") = ChartTagDetailFragment().also {
            it.arguments = Bundle().apply {
                putString(ARG_PARAM_TAG, tag)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: FragmentChartTagDetailViewModel
    private val albumInfo by lazy { DataInfo() }
    private val artistInfo by lazy { DataInfo() }
    private val trackInfo by lazy { DataInfo() }
    private var albumRes = mutableListOf<AlbumEntity.Album>()
    private var artistRes = mutableListOf<ArtistEntity.Artist>()
    private var trackRes = mutableListOf<TrackEntity.Track>()

    // Get the arguments from the bundle here.
    private val musicTag: String by lazy { this.arguments.getString(ARG_PARAM_TAG) }

    //region Lifecycle
    override fun onResume() {
        super.onResume()
        RxBus.get().register(this)
    }

    override fun onStop() {
        super.onStop()
        RxBus.get().unregister(this)
    }
    //endregion

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            //            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//            artistAdapter = BaseDataBindingAdapter<ItemArtistType2Binding, ArtistEntity.Artist>(R.layout.item_artist_type_2,
//                artistRes) { holder, item ->
//                holder.binding.avm = RecyclerViewChartSimilarArtistViewModel(item).apply {
//                    onAttach(this@ChartTagDetailFragment)
//                    clickItemListener = {}
//                }
//            }
//            artistDecoration = HorizontalItemDecorator(20)
//        }
//        viewModel.fetchDetailInfo(mbid, artistName) {
//            // Refresh the similar artist recyclerview.
//            it.artist?.similar?.artists?.let {
//                artistRes = (binding?.artistAdapter as SimilarArtistAdapter to artistRes).refreshRecyclerView {
//                    addAll(it)
//                }
//            }
        }
        albumInfo.page.takeIf { 1 >= it && albumInfo.canLoadMoreFlag }?.let {
            viewModel.fetchHotAlbum(musicTag, albumInfo.page, albumInfo.limit) { resList, total ->
            }
        }
//        artistInfo.page.takeIf { 1 >= it && artistInfo.canLoadMoreFlag }?.let {
//            viewModel.fetchHotArtist(musicTag, artistInfo.page, artistInfo.limit) { resList, total ->
//            }
//        }
//        trackInfo.page.takeIf { 1 >= it && trackInfo.canLoadMoreFlag }?.let {
//            viewModel.fetchHotTrack(musicTag, trackInfo.page, trackInfo.limit) { resList, total ->
//            }
//        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_tag
    //endregion
}