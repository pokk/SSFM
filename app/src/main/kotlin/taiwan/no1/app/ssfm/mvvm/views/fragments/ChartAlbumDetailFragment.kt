package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentDetailAlbumBinding
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartAlbumDetailViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class ChartAlbumDetailFragment: AdvancedFragment<FragmentChartAlbumDetailViewModel, FragmentDetailAlbumBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_ARTIST_ALBUM_NAME: String = "param_artist_album_name"
        private const val ARG_PARAM_ARTIST_NAME: String = "param_artist_name"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] ChartArtistDetailFragment.
         */
        fun newInstance(artistAlbumName: String = "", artistName: String = "") = ChartAlbumDetailFragment().also {
            it.arguments = Bundle().apply {
                putString(ARG_PARAM_ARTIST_ALBUM_NAME, artistAlbumName)
                putString(ARG_PARAM_ARTIST_NAME, artistName)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: FragmentChartAlbumDetailViewModel
    private var tagRes = mutableListOf<BaseEntity>()
    private var trackRes = mutableListOf<BaseEntity>()
    // Get the arguments from the bundle here.
    private val artistAlbumName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_ALBUM_NAME) }
    private val artistName: String by lazy { this.arguments.getString(ARG_PARAM_ARTIST_NAME) }

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_detail_album
    //endregion
}