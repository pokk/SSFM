package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.widget.GridLayout.VERTICAL
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentChartIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemArtistType1Binding
import taiwan.no1.app.ssfm.databinding.ItemTagType1Binding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.ArtistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.TagAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.resCallback
import taiwan.no1.app.ssfm.misc.extension.scaledDrawable
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartIndexViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewArtistChartViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewTagChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.GridSpacingItemDecorator
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator
import javax.inject.Inject


/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class ChartIndexFragment: AdvancedFragment<FragmentChartIndexViewModel, FragmentChartIndexBinding>() {
    @Inject override lateinit var viewModel: FragmentChartIndexViewModel
    private val artistInfo by lazy { DataInfo() }
    private val tagInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<ArtistEntity.Artist>()
    private var tagRes = mutableListOf<TagEntity.Tag>()

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistAdapter = BaseDataBindingAdapter<ItemArtistType1Binding, ArtistEntity.Artist>(R.layout.item_artist_type_1,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewArtistChartViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                }
                val sd = App.compactContext.scaledDrawable(R.drawable.lb_ic_thumb_up_outline, 0.5f, 0.5f)
                holder.binding.tvPlayCount.setCompoundDrawables(sd, null, null, null)
            }
            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as ArtistAdapter, artistInfo,
                artistRes, viewModel::fetchArtistList)
            artistDecoration = HorizontalItemDecorator(20)

            tagLayoutManager = StaggeredGridLayoutManager(3, VERTICAL)
            tagAdapter = BaseDataBindingAdapter<ItemTagType1Binding, TagEntity.Tag>(R.layout.item_tag_type_1,
                tagRes) { holder, item ->
                holder.binding.avm = RecyclerViewTagChartViewModel(item).apply {
                    onAttach(this@ChartIndexFragment)
                }
            }
            tagDecoration = GridSpacingItemDecorator(3, 20, false)

        }
        // First time showing this fragment.
        artistInfo.page.takeIf { 1 >= it && artistInfo.canLoadMoreFlag }?.let {
            viewModel.fetchArtistList(artistInfo.page, artistInfo.limit) { resList, total ->
                artistRes = resCallback(resList, total, artistRes, binding?.artistAdapter as ArtistAdapter, artistInfo)
            }
        }
        tagInfo.page.takeIf { 1 >= it && tagInfo.canLoadMoreFlag }?.let {
            viewModel.fetchTrackList(tagInfo.page, tagInfo.limit) { resList, total ->
                tagRes = resCallback(resList, total, tagRes, binding?.tagAdapter as TagAdapter, tagInfo)
                logw(tagRes)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_chart_index
    //endregion
}