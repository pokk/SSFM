package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentChartIndexBinding
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartIndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class ChartIndexFragment: AdvancedFragment<FragmentChartIndexViewModel, FragmentChartIndexBinding>() {
    @Inject override lateinit var viewModel: FragmentChartIndexViewModel
    private val artistInfo by lazy { DataInfo() }
    //    private val trackInfo by lazy { DataInfo() }
    private var artistRes = mutableListOf<ArtistEntity.Artist>()
//    private var trackRes = mutableListOf<TrackEntity.Track>()

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_chart_index
    //endregion
}