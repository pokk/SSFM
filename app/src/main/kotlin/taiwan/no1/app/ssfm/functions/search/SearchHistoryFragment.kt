package taiwan.no1.app.ssfm.functions.search

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchHistoryBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchHistoryType1Binding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.HistoryAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshRecyclerView
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.RemoveKeywordHistoriesCase
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchHistoryFragment : AdvancedFragment<SearchHistoryFragmentViewModel, FragmentSearchHistoryBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] SearchHistoryFragment.
         */
        fun newInstance() = SearchHistoryFragment()
    }
    //endregion

    @Inject override lateinit var viewModel: SearchHistoryFragmentViewModel
    // This usecase is for each items of the recyclerview.
    @Inject lateinit var deleteUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>
    private var searchRes = mutableListOf<BaseEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        searchRes.clear()
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = BaseDataBindingAdapter<ItemSearchHistoryType1Binding, BaseEntity>(R.layout.item_search_history_type_1,
                searchRes) { holder, item ->
                holder.binding.avm = RecyclerViewSearchHistoryViewModel(item,
                    activity,
                    deleteUsecase).
                    apply { deleteItemListener = deleteItem }
            }
        }

        viewModel.fetchHistoryList { searchRes.refreshRecyclerView { addAll(it) } }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_history
    //endregion

    /** An anonymous callback function for a delete event from the viewholder. */
    private val deleteItem = { entity: KeywordEntity, isSuccess: Boolean ->
        if (isSuccess) {
            searchRes.refreshRecyclerView { remove(entity) }
        }
    }

    /**
     * The operation for updating the list searchResult by the adapter. Including updating
     * the original list and the showing list on the recycler view.
     *
     * @hashCode block the block operation for new data list.
     * @return a new updated list.
     */
    private fun MutableList<BaseEntity>.refreshRecyclerView(block: ArrayList<BaseEntity>.() -> Unit) =
        (binding?.adapter as HistoryAdapter to this).refreshRecyclerView(block)
}