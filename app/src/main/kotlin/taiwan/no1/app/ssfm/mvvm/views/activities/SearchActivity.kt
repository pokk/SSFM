package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivitySearchBinding
import taiwan.no1.app.ssfm.misc.extension.addFragment
import taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchResultFragment
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   9/16/17
 */
class SearchActivity: AdvancedActivity<SearchViewModel, ActivitySearchBinding>() {
    @Inject override lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.addFragment(R.id.fl_container, SearchResultFragment())
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_search)
}