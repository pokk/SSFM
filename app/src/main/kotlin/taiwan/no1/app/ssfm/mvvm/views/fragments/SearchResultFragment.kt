package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchResultBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecyclerViewScrollCallback
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchResultViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewSearchMusicResultViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment: AdvancedFragment<FragmentSearchResultViewModel, FragmentSearchResultBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchResultViewModel
    var keyword: String = ""
    private var res = mutableListOf<InfoBean>()
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
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            adapter = BaseDataBindingAdapter<ItemSearchMusicType1Binding, InfoBean>(R.layout.item_search_music_type_1,
                res) { holder, item ->
                holder.binding.avm = RecyclerViewSearchMusicResultViewModel(item, activity)
            }
            layoutManager = WrapContentLinearLayoutManager(activity)
            loadmore = object: RecyclerViewScrollCallback {
                override fun loadMoreEvent(recyclerView: RecyclerView, total: Int) {
                    if (resInfo.canLoadMoreFlag && !resInfo.isLoading) {
                        resInfo.isLoading = true
                        val requestPage = Math.ceil(total / Constant.QUERY_PAGE_SIZE.toDouble()).toInt() + 1
                        viewModel.sendSearchRequest(keyword, requestPage, resultCallback = updateListInfo)
                    }
                }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
    //endregion

    /**
     * An anonymous callback function for updating the recyclerview list and the item lists from the
     * viewholder of the loading more event.
     */
    private val updateListInfo = { keyword: String, musics: MutableList<InfoBean>, canLoadMore: Boolean ->
        this.keyword = keyword
        res = (binding?.adapter as BaseDataBindingAdapter<ItemSearchMusicType1Binding, InfoBean>).
            refresh(res, ArrayList(res).apply { addAll(musics) }).toMutableList()
        // TODO(jieyi): 9/28/17 Close the loading item or view.
        resInfo.isLoading = false
        // Raise the stopping loading more data flag for avoiding to load again.
        resInfo.canLoadMoreFlag = canLoadMore
    }
}