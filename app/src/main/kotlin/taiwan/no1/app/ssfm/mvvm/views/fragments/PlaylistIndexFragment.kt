package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentMylistIndexBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentPlaylistIndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import javax.inject.Inject

/**
 * @author  jieyi
 * @since   11/5/17
 */
class PlaylistIndexFragment: AdvancedFragment<FragmentPlaylistIndexViewModel, FragmentMylistIndexBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] PlaylistIndexFragment.
         */
        fun newInstance() = PlaylistIndexFragment().also {
            it.arguments = Bundle().also {}
        }
    }
    //endregion

    @Inject override lateinit var viewModel: FragmentPlaylistIndexViewModel

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_mylist_index
    //endregion
}