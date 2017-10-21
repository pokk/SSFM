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
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_: String = "param_"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(arg1: String) = ChartArtistDetailFragment().also {
            it.arguments = Bundle().also {
                it.putString(ARG_PARAM_, arg1)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: FragmentChartArtistDetailViewModel

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_artist
    //endregion
}