package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import org.jetbrains.anko.act
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailArtistBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType2Binding
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.SimilarArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshRecyclerView
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartArtistDetailViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewChartSimilarArtistViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.activities.ChartActivity
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
    private var artistRes = mutableListOf<ArtistEntity.Artist>()
    // Get the arguments from the bundle here.
    private val mbid: String by lazy { this.arguments.getString(ARG_PARAM_MBID) }
    private val artistName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_NAME) }

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
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = BaseDataBindingAdapter<ItemArtistType2Binding, ArtistEntity.Artist>(R.layout.item_artist_type_2,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartSimilarArtistViewModel(item).apply {
                    onAttach(this@ChartArtistDetailFragment)
                    clickItemListener = {}
                }
            }
            artistDecoration = HorizontalItemDecorator(20)
        }
        viewModel.fetchDetailInfo(mbid, artistName) {
            // Refresh the similar artist recyclerview.
            it.artist?.similar?.artists?.let {
                artistRes = (binding?.artistAdapter as SimilarArtistAdapter to artistRes).refreshRecyclerView {
                    addAll(it)
                }
            }
        }
        viewModel.fetchHotAlbum(artistName)
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_artist
    //endregion

    /**
     * @param mbid
     *
     * @event_from [taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewChartSimilarArtistViewModel.artistOnClick]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.VIEWMODEL_CLICK_SIMILAR)))
    fun receiveSubFragmentEvent(artistName: String) {
        (act as ChartActivity).navigate(ChartArtistDetailFragment.newInstance(artistName = artistName), true)
    }
}