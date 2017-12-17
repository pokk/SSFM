package taiwan.no1.app.ssfm.features.search

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.util.SparseArray
import com.devrapid.kotlinknifer.addFragment
import kotlinx.android.synthetic.main.bottomsheet_track.rl_bottom_sheet
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivitySearchBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.features.bottomsheet.BottomSheetViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.CALLBACK_SPARSE_INDEX_FOG_COLOR
import taiwan.no1.app.ssfm.misc.constants.Constant.CALLBACK_SPARSE_INDEX_IMAGE_URL
import taiwan.no1.app.ssfm.misc.constants.Constant.CALLBACK_SPARSE_INDEX_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.FRAGMENT_SEARCH_HISTORY
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.FRAGMENT_SEARCH_INDEX
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.FRAGMENT_SEARCH_RESULT
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import java.util.Stack
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   9/16/17
 */
class SearchActivity : AdvancedActivity<SearchViewModel, ActivitySearchBinding>() {
    @Inject override lateinit var viewModel: SearchViewModel
    @Inject lateinit var addPlaylistItemCase: AddPlaylistItemCase
    /** For judging a fragment should be pushed or popped. */
    private val fragmentStack by lazy { Stack<Fragment>() }

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.bottomSheetVm = BottomSheetViewModel(BottomSheetBehavior.from(rl_bottom_sheet), addPlaylistItemCase)
        addFragmentAndToStack(SearchIndexFragment.newInstance())
        viewModel.apply {
            navigateListener = { fragmentTag, params ->
                params?.let { navigate(fragmentTag, params) } ?: navigate(fragmentTag)
            }
            popFragment = { toFragmentTag -> this@SearchActivity.popFragmentAndFromStack(toFragmentTag) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.bottomSheetVm = null
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId() = this to R.layout.activity_search
    //endregion

    override fun onBackPressed() {
        super.onBackPressed()
        when (fragmentStack.safePeek()) {
            is SearchHistoryFragment -> viewModel.collapseSearchView()
        // OPTIMIZE(jieyi): 10/4/17 Just workaround. Clear the focus, only from result view to history view.
            is SearchResultFragment -> binding.includeToolbar?.svMusicSearch?.clearFocus()
        }
        fragmentStack.safePop()
    }

    private fun navigate(fragmentTag: String, params: SparseArray<Any> = SparseArray()) {
        runNewFragment(fragmentTag, params).let { targetFragment ->
            if (isSpecificTargetAction(fragmentTag) || fragmentStack.safePeek() is SearchResultFragment) {
                if (popFragmentAndFromStack(FRAGMENT_SEARCH_HISTORY))
                    return@let
            }
            addFragmentAndToStack(targetFragment, true)
        }
    }

    /**
     * Check the specific fragment. The following of the specific fragment are
     * 1. When search some tracks in the [SearchHistoryFragment], we don't want to add a same fragment
     *    again and again.
     *
     * @param fragmentTag the tag of a fragment.
     * @return [true] if the fragment is a specific fragment; otherwise [false].
     */
    private fun isSpecificTargetAction(fragmentTag: String): Boolean =
        FRAGMENT_SEARCH_HISTORY == fragmentTag && (fragmentStack.safePeek() is SearchHistoryFragment || fragmentStack.safePeek() is SearchResultFragment)

    /**
     * According to the [tag] to adding a new [Fragment] with the parameters.
     *
     * @param tag the tag of a fragment.
     * @param params the parameters for the new fragment.
     */
    private fun runNewFragment(tag: String, params: SparseArray<Any>) = when (tag) {
        FRAGMENT_SEARCH_RESULT -> {
            val keyword = params[CALLBACK_SPARSE_INDEX_KEYWORD] as String
            val imageUrl = params[CALLBACK_SPARSE_INDEX_IMAGE_URL] as String
            val fgFogColor = params[CALLBACK_SPARSE_INDEX_FOG_COLOR] as Int
            SearchResultFragment.newInstance(keyword, imageUrl, fgFogColor)
        }
        FRAGMENT_SEARCH_HISTORY -> SearchHistoryFragment.newInstance()
        FRAGMENT_SEARCH_INDEX -> SearchIndexFragment.newInstance()
        else -> error("There's no kind of the fragment tag.")
    }

    /**
     * Add a fragment to the [getFragmentManager] and [fragmentStack].
     *
     * @param fragment [Fragment]
     * @param needBack when the use clicks the return button, the page will transfer to the previous one.
     */
    private fun addFragmentAndToStack(fragment: Fragment, needBack: Boolean = false) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
        fragmentStack.push(fragment)
    }

    private fun popFragmentAndFromStack(toFragmentTag: String): Boolean {
        // For staying the same history fragment.
        if (FRAGMENT_SEARCH_HISTORY == toFragmentTag && fragmentStack.safePeek() is SearchHistoryFragment)
            return true
        // This is very special case for clicking a artist or a track from the search index fragment.
        // OPTIMIZE(jieyi): 10/18/17 Here may modified better!?
        if (FRAGMENT_SEARCH_HISTORY == toFragmentTag && fragmentStack.safePeek() is SearchResultFragment) {
            fragmentManager.popBackStackImmediate()
            fragmentStack.safePop()
            return false
        }

        fragmentManager.popBackStackImmediate()
        fragmentStack.safePop()
        return true
    }

    private fun <E> Stack<E>.safePop() = lastOrNull()?.let { pop() }

    private fun <E> Stack<E>.safePeek() = lastOrNull()?.let { peek() }
}