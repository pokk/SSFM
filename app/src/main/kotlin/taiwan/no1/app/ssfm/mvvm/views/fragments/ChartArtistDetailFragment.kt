package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailArtistBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartArtistDetailViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class ChartArtistDetailFragment: AdvancedFragment<FragmentChartArtistDetailViewModel, FragmentDetailArtistBinding>() {
    @Inject override lateinit var viewModel: FragmentChartArtistDetailViewModel

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_artist
    //endregion
}