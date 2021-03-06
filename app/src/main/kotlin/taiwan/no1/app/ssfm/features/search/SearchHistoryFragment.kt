package taiwan.no1.app.ssfm.features.search

import android.os.Bundle
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchHistoryBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.HistoryAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshRecyclerView
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.DeleteSearchHistoryCase
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
        fun newInstance() = SearchHistoryFragment().apply {
            arguments = bundleOf()
        }
    }
    //endregion

    @Inject override lateinit var viewModel: SearchHistoryFragmentViewModel
    // This usecase is for each items of the recyclerview.
    @Inject lateinit var deleteUsecase: DeleteSearchHistoryCase
    private var searchRes = mutableListOf<BaseEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        searchRes.clear()
    }

    override fun onDestroyView() {
        (binding?.adapter as BaseDataBindingAdapter<*, *>).detachAll()
        super.onDestroyView()
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = HistoryAdapter(this@SearchHistoryFragment,
                                     R.layout.item_search_history_type_1,
                                     searchRes) { holder, item, _ ->
                if (null == holder.binding.avm)
                    holder.binding.avm = RecyclerViewSearchHistoryViewModel(item, activity, deleteUsecase)
                        .apply { deleteItemListener = deleteItem }
                else
                    holder.binding.avm?.setKeywordItem(item)
            }
        }

        viewModel.fetchHistoryList { searchRes.refreshRecyclerView { addAll(it) } }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_history
    //endregion

    /** An anonymous callback function for a delete event from the viewholder. */
    private val deleteItem = { entity: KeywordEntity, isSuccess: Boolean ->
        if (isSuccess) searchRes.refreshRecyclerView { remove(entity) }
    }

    /**
     * The operation for updating the list searchResult by the adapter. Including updating
     * the original list and the showing list on the recycler view.
     *
     * @param block the block operation for new data list.
     * @return a new updated list.
     */
    private fun MutableList<BaseEntity>.refreshRecyclerView(block: ArrayList<BaseEntity>.() -> Unit) =
        (binding?.adapter as HistoryAdapter to this).refreshRecyclerView(block)
}