package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_search_history.rv_search_history
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchHistoryBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchHistoryType1Binding
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchHistoryViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewSearchHistoryViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchHistoryFragment: AdvancedFragment<FragmentSearchHistoryViewModel, FragmentSearchHistoryBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchHistoryViewModel
    private var adapter by Delegates.notNull<BaseDataBindingAdapter<ItemSearchHistoryType1Binding, KeywordEntity>>()
    private var res = mutableListOf<KeywordEntity>()

    override fun init(savedInstanceState: Bundle?) {
        adapter = BaseDataBindingAdapter(R.layout.item_search_history_type_1, res) { block, item ->
            block.binding.avm = RecyclerViewSearchHistoryViewModel(item, activity)
        }
        rv_search_history.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = this@SearchHistoryFragment.adapter
        }

        viewModel.fetchHistoryList { histories ->
            res = res.let {
                it.clear()
                adapter.refresh(it, ArrayList(it).apply { addAll(histories) }).toMutableList()
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_history
}