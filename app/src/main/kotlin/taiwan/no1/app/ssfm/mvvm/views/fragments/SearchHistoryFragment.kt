package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
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

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchHistoryFragment: AdvancedFragment<FragmentSearchHistoryViewModel, FragmentSearchHistoryBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchHistoryViewModel
    // This usecase is for each items of the recyclerview.
    @Inject lateinit var deleteUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>
    private var res = mutableListOf<KeywordEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        res.clear()
    }
    //endregion

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            adapter = BaseDataBindingAdapter<ItemSearchHistoryType1Binding, KeywordEntity>(R.layout.item_search_history_type_1,
                res) { holder, item ->
                holder.binding.avm = RecyclerViewSearchHistoryViewModel(item, activity, deleteUsecase).
                    apply { deleteItemListener = deleteItem }
            }
            layoutManager = WrapContentLinearLayoutManager(activity)
        }

        viewModel.fetchHistoryList { res.refreshRecyclerView { addAll(it) } }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_history
    //endregion

    /** An anonymous callback function for a delete event from the viewholder. */
    private val deleteItem = { entity: KeywordEntity, isSuccess: Boolean ->
        // TODO(jieyi): 10/1/17 Delete all items.
        if (isSuccess) {
            res = res.refreshRecyclerView { remove(entity) }
        }
    }

    /**
     * The operation for updating the list result by the adapter. Including updating the original list
     * and the showing list on the recycler view.
     *
     * @param block the block operation for new data list.
     * @return a new updated list.
     */
    private fun MutableList<KeywordEntity>.refreshRecyclerView(block: ArrayList<KeywordEntity>.() -> Unit): MutableList<KeywordEntity> =
        (binding?.adapter as BaseDataBindingAdapter<ItemSearchHistoryType1Binding, KeywordEntity>).
            refresh(this, ArrayList(this).apply(block)).toMutableList()
}