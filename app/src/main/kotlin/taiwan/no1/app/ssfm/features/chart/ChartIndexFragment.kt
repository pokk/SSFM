package taiwan.no1.app.ssfm.features.chart

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.GridLayout.VERTICAL
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.GridSpacingItemDecorator
import com.devrapid.kotlinknifer.recyclerview.itemdecorator.HorizontalItemDecorator
import org.jetbrains.anko.act
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentChartIndexBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.features.search.RecyclerViewSearchArtistChartViewModel
import taiwan.no1.app.ssfm.misc.extension.gContext
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RankAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TagAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.keepAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.extension.recyclerview.restoreAllLastItemPosition
import taiwan.no1.app.ssfm.misc.extension.scaledDrawable
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartIndexFragment : AdvancedFragment<ChartIndexFragmentViewModel, FragmentChartIndexBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartIndexFragment.
         */
        fun newInstance() = ChartIndexFragment().apply {
            arguments = bundleOf()
        }
    }
    //endregion

    @Inject override lateinit var viewModel: ChartIndexFragmentViewModel
    private val rankInfo by lazy { DataInfo() }
    private val artistInfo by lazy { DataInfo(limit = 30) }
    private val tagInfo by lazy { DataInfo() }
    private var rankRes = mutableListOf<BaseEntity>()
    private var artistRes = mutableListOf<BaseEntity>()
    private var tagRes = mutableListOf<BaseEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        binding?.apply {
            listOf(Pair(artistInfo, artistLayoutManager),
                Pair(rankInfo, rankLayoutManager)).restoreAllLastItemPosition()
        }
    }

    override fun onPause() {
        super.onPause()
        binding?.apply {
            listOf(Triple(artistInfo, rvTopArtists, artistLayoutManager),
                Triple(rankInfo, rvTopChart, rankLayoutManager)).keepAllLastItemPosition()
        }
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            rankLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            rankAdapter = RankAdapter(R.layout.item_rank_type_1, rankRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartRankChartViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                }
            }
            rankDecoration = HorizontalItemDecorator(20)

            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = ArtistAdapter(R.layout.item_artist_type_1, artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewSearchArtistChartViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                    clickItemListener = {
                        // TODO(jieyi): 10/22/17 Change fragment to create instance method.
                        (act as ChartActivity).navigate(ChartArtistDetailFragment.newInstance(it.mbid.orEmpty(),
                            it.name.orEmpty()), true)
                    }
                }
                val sd = gContext().scaledDrawable(R.drawable.ic_feature, 0.5f, 0.5f)
                holder.binding.tvPlayCount.setCompoundDrawables(sd, null, null, null)
            }
            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as ArtistAdapter, artistInfo,
                artistRes, viewModel::fetchArtistList)
            artistDecoration = HorizontalItemDecorator(20)

            tagLayoutManager = StaggeredGridLayoutManager(3, VERTICAL)
            tagAdapter = TagAdapter(R.layout.item_tag_type_1, tagRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartTagViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                    clickItemListener = {
                        // TODO(jieyi): 10/22/17 Change fragment to create instance method.
                        (act as ChartActivity).navigate(ChartTagDetailFragment.newInstance(it.name.orEmpty()), true)
                    }
                }
            }
            tagDecoration = GridSpacingItemDecorator(3, 20, false)
        }
        // First time showing this fragment.
        rankInfo.firstFetch { info ->
            viewModel.fetchRankList {
                rankRes.refreshAndChangeList(it, 1, binding?.rankAdapter as RankAdapter, info)
            }
        }
        artistInfo.firstFetch {
            viewModel.fetchArtistList(it.page, it.limit) { resList, total ->
                artistRes.refreshAndChangeList(resList, total, binding?.artistAdapter as ArtistAdapter, it)
            }
        }
        tagInfo.firstFetch {
            viewModel.fetchTrackList(it.page, it.limit) { resList, total ->
                tagRes.refreshAndChangeList(resList, total, binding?.tagAdapter as TagAdapter, it)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_chart_index
    //endregion
}