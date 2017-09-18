package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.part_toolbar_view.tv_menu_title
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.PlayMainViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainActivity: AdvancedActivity<PlayMainViewModel, ActivityMusicBinding>() {
    @Inject override lateinit var viewModel: PlayMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_menu_title.text = "Play"
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_music)
}