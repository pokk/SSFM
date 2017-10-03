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
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   9/16/17
 */
class SearchActivity: AdvancedActivity<SearchViewModel, ActivitySearchBinding>() {
    @Inject override lateinit var viewModel: SearchViewModel
    private val searchFragments by lazy {
        hashMapOf<String, Fragment>(FRAGMENT_SEARCH_RESULT to SearchResultFragment(),
            FRAGMENT_SEARCH_HISTORY to SearchHistoryFragment(),
            FRAGMENT_SEARCH_INDEX to SearchIndexFragment())
    }

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.addFragment(R.id.fl_container, searchFragments[FRAGMENT_SEARCH_INDEX] as Fragment)
        viewModel.navigateListener = { fragmentTag, params ->
            params?.let { navigate(fragmentTag, params) } ?: navigate(fragmentTag)
        }
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_search)
    //endregion

    private fun navigate(fragmentTag: String, params: SparseArray<Any> = SparseArray()) {
        setFragmentParameters(fragmentTag, params)?.let {
            fragmentManager.findFragmentByTag(it.javaClass.name).let { showingFragment ->
                if (it != showingFragment) {
                    fragmentManager.addFragment(R.id.fl_container, it)
                }
            }
        }
    }

    private fun setFragmentParameters(tag: String, params: SparseArray<Any>) = searchFragments[tag].also {
        when (tag) {
            FRAGMENT_SEARCH_RESULT -> (it as SearchResultFragment).keyword = params[0] as? String ?: ""
            FRAGMENT_SEARCH_HISTORY -> Unit
            FRAGMENT_SEARCH_INDEX -> Unit
            else -> Unit
        }
    }
}