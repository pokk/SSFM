package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchHistoryBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchHistoryViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchHistoryFragment: AdvancedFragment<FragmentSearchHistoryViewModel, FragmentSearchHistoryBinding>() {
    override var viewModel: FragmentSearchHistoryViewModel = FragmentSearchHistoryViewModel()

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_history
}