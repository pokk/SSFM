package taiwan.no1.app.ssfm.functions.search

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchResultBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecyclerViewScrollCallback
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.FetchMusicDetailCase
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment : AdvancedFragment<SearchResultFragmentViewModel, FragmentSearchResultBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the
         * provided parameters.
         *
         * @return A new instance of [android.app.Fragment] SearchResultFragment.
         */
        fun newInstance() = SearchResultFragment()
    }
    //endregion

    @Inject override lateinit var viewModel: SearchResultFragmentViewModel
    @Inject lateinit var usecase: FetchMusicDetailCase
    var keyword: String = ""
    private var res = mutableListOf<BaseEntity>()
    private val resInfo by lazy { DataInfo() }

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        // Due to this object is kept by `SearchActivity`, this list need to be cleared every time.
        res.clear()
        resInfo.isLoading = true
        viewModel.sendSearchRequest(keyword, resultCallback = updateListInfo)
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = BaseDataBindingAdapter<ItemSearchMusicType1Binding, BaseEntity>(R.layout.item_search_music_type_1,
                res) { holder, item ->
                holder.binding.avm = RecyclerViewSearchMusicResultViewModel(item, activity.applicationContext, usecase)
            }
            loadmore = object : RecyclerViewScrollCallback {
                override fun loadMoreEvent(recyclerView: RecyclerView, total: Int) {
                    if (resInfo.canLoadMoreFlag && !resInfo.isLoading) {
                        resInfo.isLoading = true
                        // OPTIMIZE(jieyi): 11/21/17 Modify the page constant.
                        viewModel.sendSearchRequest(keyword, res.size / 10, updateListInfo)
                    }
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
    //endregion

    /**
     * An anonymous callback function for updating the recyclerview list and the item lists
     * from the viewholder of the loading more event.
     */
    private val updateListInfo = { keyword: String, musics: MutableList<MusicEntity.Music>, canLoadMore: Boolean ->
        this.keyword = keyword
        logw(musics)
        res = (binding?.adapter as BaseDataBindingAdapter<ItemSearchMusicType1Binding, BaseEntity>).
            refresh(res, ArrayList(res).apply { addAll(musics) }).toMutableList()
        // TODO(jieyi): 9/28/17 Close the loading item or view.
        resInfo.isLoading = false
        // Raise the stopping loading more data flag for avoiding to load again.
        resInfo.canLoadMoreFlag = canLoadMore
    }
}