package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import android.util.SparseArray
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivitySearchBinding
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
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
        hashMapOf<String, Fragment>(RxBusConstant.FRAGMENT_SEARCH_RESULT to SearchResultFragment(),
            RxBusConstant.FRAGMENT_SEARCH_HISTORY to SearchHistoryFragment(),
            RxBusConstant.FRAGMENT_SEARCH_INDEX to SearchIndexFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.addFragment(R.id.fl_container, searchFragments[RxBusConstant.FRAGMENT_SEARCH_INDEX] as Fragment)
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_search)

    fun <D> navigate(fragmentTag: String, params: SparseArray<D> = SparseArray()) {
        searchFragments[fragmentTag]?.let {
            fragmentManager.findFragmentByTag(it.javaClass.name).let { showingFragment ->
                if (it != showingFragment) {
                    fragmentManager.addFragment(R.id.fl_container, it)
                }
            }
        }
    }
}