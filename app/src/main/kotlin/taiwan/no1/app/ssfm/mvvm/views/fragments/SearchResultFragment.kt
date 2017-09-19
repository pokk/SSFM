package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R.layout
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchIndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment: AdvancedFragment<FragmentSearchIndexViewModel, FragmentSearchIndexBinding>() {
    override var viewModel: FragmentSearchIndexViewModel = FragmentSearchIndexViewModel()

    override fun init(savedInstanceState: Bundle?) {
    }

    override fun provideInflateView(): Int = layout.fragment_search_index
}