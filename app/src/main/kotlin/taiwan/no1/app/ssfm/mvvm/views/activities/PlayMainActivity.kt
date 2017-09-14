package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityPlayMusicBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.PlayMainViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainActivity: AdvancedActivity<PlayMainViewModel, ActivityPlayMusicBinding>() {
    @Inject override lateinit var viewModel: PlayMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.attachMenuView()
        navigator.activity
    }

    // TODO(jieyi): 9/10/17 This layout is wrong, I just set fragment temporally.
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_play_music)
}