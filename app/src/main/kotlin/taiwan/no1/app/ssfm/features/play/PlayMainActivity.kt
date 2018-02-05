package taiwan.no1.app.ssfm.features.play

import android.os.Bundle
import kotlinx.android.synthetic.main.part_main_play_music.rcii_album
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainActivity : AdvancedActivity<PlayMainViewModel, ActivityMusicBinding>() {
    @Inject override lateinit var viewModel: PlayMainViewModel

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rcii_album.apply {
            startTime = playerHelper.currentTime
            endTime = playerHelper.trackDuration
            onClickEvent = { view, isPause ->
                playerHelper.apply { if (isPause) pause() else play() }
            }
        }
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId() = this to R.layout.activity_music
    //endregion
}