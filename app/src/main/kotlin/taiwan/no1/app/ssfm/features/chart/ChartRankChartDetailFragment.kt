package taiwan.no1.app.ssfm.features.chart

import android.os.Bundle
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.VerticalItemDecorator
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentRankChartDetailBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.HELPER_ADD_TO_PLAYLIST
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RankChartDetailAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import javax.inject.Inject
import javax.inject.Named

/**
 * @author  jieyi
 * @since   11/24/17
 */
class ChartRankChartDetailFragment : AdvancedFragment<ChartRankChartDetailFragmentViewModel, FragmentRankChartDetailBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_RANK_CODE: String = "param_music_rank_code"
        private const val ARG_PARAM_CHART_ENTITY: String = "param_music_chart_entity"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(code: Int = Constant.SPECIAL_NUMBER, chartEntity: RankChartEntity? = null) =
            ChartRankChartDetailFragment().apply {
                arguments = bundleOf(ARG_PARAM_RANK_CODE to code,
                                     ARG_PARAM_CHART_ENTITY to (chartEntity ?: RankChartEntity())).apply {
                }
            }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartRankChartDetailFragmentViewModel
    @field:[Inject Named("add_playlist_item")] lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val trackInfo by lazy { DataInfo() }
    private var trackRes = mutableListOf<PlaylistItemEntity>()
    // Get the arguments from the bundle here.
    private val rankCode by lazy { arguments.getInt(ARG_PARAM_RANK_CODE) }
    private val chartEntity: RankChartEntity? by lazy { arguments.getParcelable<RankChartEntity>(ARG_PARAM_CHART_ENTITY) }

    //region Fragment lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
    }

    override fun onDestroyView() {
        (binding?.trackAdapter as BaseDataBindingAdapter<*, *>).detachAll()
        super.onDestroyView()
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            trackLayoutManager = WrapContentLinearLayoutManager(activity)

            trackAdapter = RankChartDetailAdapter(this@ChartRankChartDetailFragment,
                                                  R.layout.item_music_type_6,
                                                  trackRes) { holder, item, index ->
                if (null == holder.binding.avm)
                    holder.binding.avm = RecyclerViewRankChartDetailViewModel(addPlaylistItemCase,
                                                                              item,
                                                                              index + 1)
                else
                    holder.binding.avm?.setMusicItem(item, index + 1)
            }

            trackDecoration = VerticalItemDecorator(20)
        }
        // First time showing this fragment.
        trackInfo.firstFetch {
            viewModel.fetchRankChartDetail(rankCode, chartEntity) {
                trackRes.refreshAndChangeList(it, 0, binding?.trackAdapter as RankChartDetailAdapter, trackInfo)
            }
        }
        playerHelper.currentObject = this.javaClass.name
    }

    override fun provideInflateView(): Int = R.layout.fragment_rank_chart_detail
    //endregion

    @Subscribe(tags = [Tag(HELPER_ADD_TO_PLAYLIST)])
    fun addToPlaylist(trackUri: String) {
        playerHelper.also {
            if (it.isFirstTimePlayHere) {
                it.clearList()
                it.playInObject = this.javaClass.name
                it.addList(trackRes.map { it.trackUri })
                it.setCurrentIndex(trackUri)
            }
        }
    }
}