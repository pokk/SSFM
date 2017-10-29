package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.addFragment
import taiwan.no1.app.ssfm.mvvm.viewmodels.ChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import taiwan.no1.app.ssfm.mvvm.views.fragments.ChartArtistDetailFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.ChartIndexFragment
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
     * @event_from [taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewChartSimilarArtistViewModel.artistOnClick]
     * @event_from [taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewUniversal2ViewModel.itemOnClick]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.VIEWMODEL_CLICK_SIMILAR)))
    fun receiveSubFragmentEvent(artistName: String) {
        navigate(ChartArtistDetailFragment.newInstance(artistName = artistName), true)
    }

    fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }
}
