package taiwan.no1.app.ssfm.functions.main

import android.app.Activity
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity: AdvancedActivity<MainViewModel, ActivityMainBinding>() {
    @Inject override lateinit var viewModel: MainViewModel

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_main)
}
