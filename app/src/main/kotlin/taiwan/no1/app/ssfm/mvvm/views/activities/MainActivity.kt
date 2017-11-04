package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMainBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.MainViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

class MainActivity: AdvancedActivity<MainViewModel, ActivityMainBinding>() {
    @Inject override lateinit var viewModel: MainViewModel

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_main)
}
