package taiwan.no1.app.ssfm.functions.chart

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.devrapid.kotlinknifer.addFragment
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.functions.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import java.util.HashMap
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class ChartActivity: AdvancedActivity<ChartViewModel, ActivityChartBinding>() {
    @Inject override lateinit var viewModel: ChartViewModel

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigate(ChartIndexFragment.newInstance(), false)
        RxBus.get().register(this)
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_chart)
    //endregion

    /**
     * @param artistName
     *
     * @event_from [taiwan.no1.app.ssfm.functions.chart.ChartViewModel.querySubmit]
     * @event_from [taiwan.no1.app.ssfm.functions.chart.RecyclerViewChartSimilarArtistViewModel.artistOnClick]
     * @event_from [taiwan.no1.app.ssfm.functions.chart.RecyclerViewUniversal2ViewModel.itemOnClick]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.VIEWMODEL_CLICK_SIMILAR)))
    fun navigateToArtistDetail(artistName: String) {
        navigate(ChartArtistDetailFragment.newInstance(artistName = artistName), true)
    }

    /**
     * @param params
     *
     * @event_from [taiwan.no1.app.ssfm.functions.chart.RecyclerViewUniversal1ViewModel.itemOnClick]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.VIEWMODEL_CLICK_ALBUM)))
    fun navigateToAlbumDetail(params: HashMap<String, String>) {
        val (artistName, artistAlbum) = (params["Artist Name"] ?: "") to (params["Artist Album Name"] ?: "")
        navigate(ChartAlbumDetailFragment.newInstance(artistAlbum, artistName), true)
    }


    fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }
}
