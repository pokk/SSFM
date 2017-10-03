package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.SparseArray
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
import java.util.*
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   9/16/17
 */
class SearchActivity: AdvancedActivity<SearchViewModel, ActivitySearchBinding>() {
    @Inject override lateinit var viewModel: SearchViewModel
    private val fragmentStack by lazy { ArrayDeque<Fragment>() }
    private val searchFragments by lazy {
        hashMapOf<String, Fragment>(FRAGMENT_SEARCH_RESULT to SearchResultFragment(),
            FRAGMENT_SEARCH_HISTORY to SearchHistoryFragment(),
            FRAGMENT_SEARCH_INDEX to SearchIndexFragment())
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

    private fun navigate(fragmentTag: String, params: SparseArray<Any> = SparseArray()) {
        // FIXME(jieyi): 10/3/17 Here has some problem between changing fragment.
        setFragmentParameters(fragmentTag, params)?.let { targetFragment ->
            if (isSpecificTargetAction(fragmentTag)) {
                return@let
            }

            addFragment(targetFragment, true)
        }
    }

    private fun isSpecificTargetAction(fragmentTag: String): Boolean {
        if (FRAGMENT_SEARCH_HISTORY == fragmentTag) {
            when (fragmentStack.peek()) {
                is SearchHistoryFragment -> return true
                is SearchResultFragment -> {
                    popFragment(FRAGMENT_SEARCH_HISTORY)
                    return true
                }
            }
        }
        return false
    }

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

    private fun popFragment(toFragmentTag: String) {
        if (FRAGMENT_SEARCH_HISTORY == toFragmentTag && fragmentStack.peek() is SearchHistoryFragment)
            return

        fragmentManager.popBackStackImmediate()
        fragmentStack.pop()
    }
}