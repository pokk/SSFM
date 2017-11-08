package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.GridLayout.VERTICAL
import org.jetbrains.anko.act
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentChartIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemTagType1Binding
import taiwan.no1.app.ssfm.functions.chart.ChartActivity
import taiwan.no1.app.ssfm.functions.chart.ChartArtistDetailFragment
import taiwan.no1.app.ssfm.functions.chart.ChartTagDetailFragment
import taiwan.no1.app.ssfm.functions.chart.RecyclerViewChartTagViewModel
import taiwan.no1.app.ssfm.functions.search.RecyclerViewSearchArtistChartViewModel
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TagAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.extension.scaledDrawable
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartIndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.GridSpacingItemDecorator
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartIndexFragment: AdvancedFragment<FragmentChartIndexViewModel, FragmentChartIndexBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartIndexFragment.
         */
        fun newInstance() = ChartIndexFragment()
    }
    //endregion

    @Inject override lateinit var viewModel: FragmentChartIndexViewModel
    private val artistInfo by lazy { DataInfo(limit = 30) }
    private val tagInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<BaseEntity>()
    private var tagRes = mutableListOf<BaseEntity>()

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, BaseEntity>(R.layout.item_artist_type_1,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewSearchArtistChartViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                    clickItemListener = {
                        // TODO(jieyi): 10/22/17 Change fragment to create instance method.
                        (act as ChartActivity).navigate(ChartArtistDetailFragment.newInstance(it.mbid ?: "",
                            it.name ?: ""), true)
                    }
                }
                val sd = App.compactContext.scaledDrawable(R.drawable.lb_ic_thumb_up_outline, 0.5f, 0.5f)
                holder.binding.tvPlayCount.setCompoundDrawables(sd, null, null, null)
            }
            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as ArtistAdapter, artistInfo,
                artistRes, viewModel::fetchArtistList)
            artistDecoration = HorizontalItemDecorator(20)

            tagLayoutManager = StaggeredGridLayoutManager(3, VERTICAL)
            tagAdapter = BaseDataBindingAdapter<ItemTagType1Binding, BaseEntity>(R.layout.item_tag_type_1,
                tagRes) { holder, item ->
                holder.binding.avm = RecyclerViewChartTagViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                    clickItemListener = {
                        // TODO(jieyi): 10/22/17 Change fragment to create instance method.
                        (act as ChartActivity).navigate(ChartTagDetailFragment.newInstance(it.name ?: ""), true)
                    }
                }
            }
            tagDecoration = GridSpacingItemDecorator(3, 20, false)
        }
        // First time showing this fragment.
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