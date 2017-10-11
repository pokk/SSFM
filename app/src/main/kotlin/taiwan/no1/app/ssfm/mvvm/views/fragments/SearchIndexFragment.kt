package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchIndexBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchIndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchIndexFragment: AdvancedFragment<FragmentSearchIndexViewModel, FragmentSearchIndexBinding>() {
    @Inject override lateinit var viewModel: FragmentSearchIndexViewModel

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        viewModel.test()
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_index
    //endregion
}