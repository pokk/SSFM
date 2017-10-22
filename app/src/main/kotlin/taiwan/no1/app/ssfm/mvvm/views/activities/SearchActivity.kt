package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.SparseArray
import com.devrapid.kotlinknifer.logi
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivitySearchBinding
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant.FRAGMENT_SEARCH_HISTORY
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant.FRAGMENT_SEARCH_INDEX
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant.FRAGMENT_SEARCH_RESULT
import taiwan.no1.app.ssfm.misc.extension.addFragment
import taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchHistoryFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchIndexFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchResultFragment
import java.util.Stack
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   9/16/17
 */
class SearchActivity: AdvancedActivity<SearchViewModel, ActivitySearchBinding>() {
    @Inject override lateinit var viewModel: SearchViewModel
    private val fragmentStack by lazy { Stack<Fragment>() }
    private val searchFragments by lazy {
        hashMapOf<String, Fragment>(FRAGMENT_SEARCH_RESULT to SearchResultFragment.newInstance(),
            FRAGMENT_SEARCH_HISTORY to SearchHistoryFragment.newInstance(),
            FRAGMENT_SEARCH_INDEX to SearchIndexFragment.newInstance())
    }

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(searchFragments[FRAGMENT_SEARCH_INDEX] as Fragment)
        viewModel.apply {
            navigateListener = { fragmentTag, params ->
                params?.let { navigate(fragmentTag, params) } ?: navigate(fragmentTag)
            }
            popFragment = { toFragmentTag -> this@SearchActivity.popFragment(toFragmentTag) }
        }
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_search)
    //endregion

    override fun onBackPressed() {
        super.onBackPressed()
        when (fragmentStack.safePeek()) {
            is SearchHistoryFragment -> viewModel.collapseSearchView()
        // OPTIMIZE(jieyi): 10/4/17 Just workaround. Clear the focus, only from result view to history view.
            is SearchResultFragment -> binding.includeToolbar.svMusicSearch.clearFocus()
        }
        fragmentStack.safePop()
    }

    private fun navigate(fragmentTag: String, params: SparseArray<Any> = SparseArray()) {
        setFragmentParameters(fragmentTag, params)?.let { targetFragment ->
            if (isSpecificTargetAction(fragmentTag) ||
                fragmentStack.safePeek() is SearchResultFragment) {
                if (popFragment(FRAGMENT_SEARCH_HISTORY))
                    return@let
            }

            addFragment(targetFragment, true)
        }
    }

    private fun isSpecificTargetAction(fragmentTag: String): Boolean =
        FRAGMENT_SEARCH_HISTORY == fragmentTag && (fragmentStack.safePeek() is SearchHistoryFragment || fragmentStack.safePeek() is SearchResultFragment)

    private fun setFragmentParameters(tag: String, params: SparseArray<Any>) = searchFragments[tag].also {
        when (tag) {
            FRAGMENT_SEARCH_RESULT -> (it as SearchResultFragment).keyword = params[0] as? String ?: ""
            FRAGMENT_SEARCH_HISTORY -> Unit
            FRAGMENT_SEARCH_INDEX -> Unit
            else -> Unit
        }
    }

    private fun addFragment(fragment: Fragment, needBack: Boolean = false) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
        fragmentStack.push(fragment)
    }

    private fun popFragment(toFragmentTag: String): Boolean {
        fragmentStack.forEach { logi(it) }
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