package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.app.Fragment
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityChartBinding
import taiwan.no1.app.ssfm.misc.extension.addFragment
import taiwan.no1.app.ssfm.mvvm.viewmodels.ChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import taiwan.no1.app.ssfm.mvvm.views.fragments.ChartIndexFragment
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class ChartActivity: AdvancedActivity<ChartViewModel, ActivityChartBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this activity using the provided parameters.
         *
         * @return A new instance of [Activity] ChartActivity.
         */
        fun newInstance() = ChartActivity()
    }
    //endregion

    @Inject override lateinit var viewModel: ChartViewModel

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigate(ChartIndexFragment(), false)
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_chart)
    //endregion

    fun navigate(fragment: Fragment, needBack: Boolean) {
        fragmentManager.addFragment(R.id.fl_container, fragment, needBack)
    }
}
