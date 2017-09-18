package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.ChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class ChartActivity: AdvancedActivity<ChartViewModel, ActivityChartBinding>() {
    @Inject override lateinit var viewModel: ChartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_chart)
}
