package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_search_history.rv_search_history
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchHistoryBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchHistoryType1Binding
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase
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
    // This usecase is for each items of the recyclerview.
    @Inject lateinit var deleteUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>
    private var adapter by Delegates.notNull<BaseDataBindingAdapter<ItemSearchHistoryType1Binding, KeywordEntity>>()
    private var res = mutableListOf<KeywordEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        res.clear()
    }
    //endregion

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        adapter = BaseDataBindingAdapter(R.layout.item_search_history_type_1, res) { block, item ->
            block.binding.avm = RecyclerViewSearchHistoryViewModel(item, activity, deleteUsecase).apply {
                deleteItemListener = deleteItem
            }
        }
        rv_search_history.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = this@SearchHistoryFragment.adapter
        }

        viewModel.fetchHistoryList {
            res = adapter.refresh(res, ArrayList(res).apply { addAll(it) }).toMutableList()
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_history
    //endregion

    private val deleteItem = { entity: KeywordEntity, isSuccess: Boolean ->
        // TODO(jieyi): 10/1/17 Delete all items.
        if (isSuccess) {
            res = adapter.refresh(res, ArrayList(res).apply { remove(entity) }).toMutableList()
        }
    }
}