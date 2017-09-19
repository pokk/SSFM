package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchResultBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchResultViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchIndexFragment: AdvancedFragment<FragmentSearchResultViewModel, FragmentSearchResultBinding>() {
    override var viewModel: FragmentSearchResultViewModel = FragmentSearchResultViewModel()

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
}