package taiwan.no1.app.ssfm.functions.chart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.VerticalItemDecorator
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentRankChartDetailBinding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RankChartDetailAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
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
            ChartRankChartDetailFragment().also {
                it.arguments = Bundle().apply {
                    putInt(ARG_PARAM_RANK_CODE, code)
                    chartEntity?.let { putParcelable(ARG_PARAM_CHART_ENTITY, it) }
                }
            }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartRankChartDetailFragmentViewModel
    @field:[Inject Named("add_playlist_item")] lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val trackInfo by lazy { DataInfo() }
    private var trackRes = mutableListOf<BaseEntity>()
    private var nestViewLastPosition = 0
    // Get the arguments from the bundle here.
    private val rankCode by lazy { arguments.getInt(ARG_PARAM_RANK_CODE) }
    private val chartEntity: RankChartEntity? by lazy { arguments.getParcelable<RankChartEntity>(ARG_PARAM_CHART_ENTITY) }

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            trackLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

            trackAdapter = RankChartDetailAdapter(R.layout.item_music_type_6, trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewRankChartDetailViewModel(addPlaylistItemCase, item).apply {
                    onAttach(this@ChartRankChartDetailFragment)
                    longClickEvent = { (activity as ChartActivity).openBottomSheet(item) }
                }
            }

            trackDecoration = VerticalItemDecorator(20)
        }
        // First time showing this fragment.
        trackInfo.firstFetch {
            viewModel.fetchRankChartDetail(rankCode, chartEntity) {
                trackRes.refreshAndChangeList(it, 0, binding?.trackAdapter as RankChartDetailAdapter, trackInfo)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_rank_chart_detail
//endregion
}