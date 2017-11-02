package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailTagBinding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType1Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType2Binding
import taiwan.no1.app.ssfm.databinding.ItemUniversalType3Binding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RVCustomScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.Universal1Adapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.Universal2Adapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.Universal3Adapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartTagDetailViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewUniversal1ViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewUniversal2ViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewUniversal3ViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.itemdecorator.HorizontalItemDecorator
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
    private var albumRes = mutableListOf<BaseEntity>()
    private var artistRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()

    // Get the arguments from the bundle here.
    private val musicTag: String by lazy { this.arguments.getString(ARG_PARAM_TAG) }

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            albumLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            artistLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            trackLayoutManager = WrapContentLinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

            albumAdapter = BaseDataBindingAdapter<ItemUniversalType1Binding, BaseEntity>(R.layout.item_universal_type_1,
                albumRes) { holder, item ->
                holder.binding.avm = RecyclerViewUniversal1ViewModel(item).apply {
                    onAttach(this@ChartTagDetailFragment)
                }
            }
            artistAdapter = BaseDataBindingAdapter<ItemUniversalType2Binding, BaseEntity>(R.layout.item_universal_type_2,
                artistRes) { holder, item ->
                holder.binding.avm = RecyclerViewUniversal2ViewModel(item).apply {
                    onAttach(this@ChartTagDetailFragment)
                }
            }
            trackAdapter = BaseDataBindingAdapter<ItemUniversalType3Binding, BaseEntity>(R.layout.item_universal_type_3,
                trackRes) { holder, item ->
                holder.binding.avm = RecyclerViewUniversal3ViewModel(item).apply {
                    onAttach(this@ChartTagDetailFragment)
                }
            }

            albumLoadmore = RVCustomScrollCallback(binding?.albumAdapter as Universal1Adapter, albumInfo,
                albumRes) { page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit ->
                viewModel.fetchHotAlbum(musicTag, page, limit, callback)
            }
            artistLoadmore = RVCustomScrollCallback(binding?.artistAdapter as Universal2Adapter, artistInfo,
                artistRes) { page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit ->
                viewModel.fetchHotArtist(musicTag, page, limit, callback)
            }
            trackLoadmore = RVCustomScrollCallback(binding?.trackAdapter as Universal3Adapter, trackInfo,
                trackRes) { page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit ->
                viewModel.fetchHotTrack(musicTag, page, limit, callback)
            }

            albumDecoration = HorizontalItemDecorator(20)
            artistDecoration = HorizontalItemDecorator(20)
            trackDecoration = HorizontalItemDecorator(20)
        }
        // First time showing this fragment.
        albumInfo.firstFetch {
            viewModel.fetchHotAlbum(musicTag, it.page, it.limit) { resList, total ->
                albumRes.refreshAndChangeList(resList, total, binding?.albumAdapter as Universal1Adapter, it)
            }
        }
        artistInfo.firstFetch {
            viewModel.fetchHotArtist(musicTag, it.page, it.limit) { resList, total ->
                artistRes.refreshAndChangeList(resList, total, binding?.artistAdapter as Universal2Adapter, it)
            }
        }
        trackInfo.firstFetch {
            viewModel.fetchHotTrack(musicTag, it.page, it.limit) { resList, total ->
                trackRes.refreshAndChangeList(resList, total, binding?.trackAdapter as Universal3Adapter, it)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_tag
    //endregion
}