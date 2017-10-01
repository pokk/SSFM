package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchResultBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchResultViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewMusicResultViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment: AdvancedFragment<FragmentSearchResultViewModel, FragmentSearchResultBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchResultViewModel
    var keyword: String = ""
    private var adapter by Delegates.notNull<BaseDataBindingAdapter<ItemSearchMusicType1Binding, InfoBean>>()
    private var res = mutableListOf<InfoBean>()
    private var isLoading = false
    private var canLoadMoreFlag = true

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        // Due to this object is kept by `SearchActivity`, this list need to be cleared every time.
        res.clear()
        isLoading = true
        viewModel.sendSearchRequest(keyword, resultCallback = updateListInfo)
    }
    //endregion

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        adapter = BaseDataBindingAdapter(R.layout.item_search_music_type_1, res) { block, item ->
            block.binding.avm = RecyclerViewMusicResultViewModel(item, activity)
        }
        binding.adapter = adapter
        binding.layoutManager = WrapContentLinearLayoutManager(activity)
        binding.loadmore = { recycler: Any?, total: Any? ->
            recycler as RecyclerView
            total as Int

            logw("hello!!!!")

            if (canLoadMoreFlag && !isLoading) {
                isLoading = true
                val requestPage = Math.ceil(total / Constant.QUERY_PAGE_SIZE.toDouble()).toInt() + 1
                viewModel.sendSearchRequest(keyword, requestPage, resultCallback = updateListInfo)
            }
        }
//        rv_music_result.apply {
//            addOnScrollListener(recyclerViewScrollListener)
//        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
    //endregion

    private val updateListInfo = { keyword: String, musics: MutableList<InfoBean>, canLoadMore: Boolean ->
        this.keyword = keyword
        res = adapter.refresh(res, ArrayList(res).apply { addAll(musics) }).toMutableList()
        // TODO(jieyi): 9/28/17 Close the loading item or view.
        isLoading = false
        // Raise the stop loading more data flag.
        canLoadMoreFlag = canLoadMore
    }

    private val recyclerViewScrollListener = BaseDataBindingAdapter.OnScrollListener {
        if (canLoadMoreFlag && !isLoading) {
            isLoading = true
            val requestPage = Math.ceil(it / Constant.QUERY_PAGE_SIZE.toDouble()).toInt() + 1
            viewModel.sendSearchRequest(keyword, requestPage, resultCallback = updateListInfo)
        }
    }
}