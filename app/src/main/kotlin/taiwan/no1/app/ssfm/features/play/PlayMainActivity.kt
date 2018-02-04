package taiwan.no1.app.ssfm.features.play

import android.os.Bundle
import com.devrapid.kotlinknifer.logw
import kotlinx.android.synthetic.main.part_main_play_music.rcii_album
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState
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
            logw(playerHelper.state, playerHelper.currentTime, playerHelper.trackDuration)
            if (MusicPlayerState.Play == playerHelper.state) start()
            startTime = playerHelper.currentTime
            endTime = playerHelper.trackDuration
            onClickEvent = { view, isPause ->
                // OPTIMIZE(jieyi): 2018/02/04 The library has some issues which cant upload new version now.
                playerHelper.apply { if (isPause) pause() else play() }
            }
        }
    }
    //endregion

    //region Base activity implement
    override fun provideBindingLayoutId() = this to R.layout.activity_music
    //endregion
}