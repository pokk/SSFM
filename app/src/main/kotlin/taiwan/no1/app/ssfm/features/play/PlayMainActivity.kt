package taiwan.no1.app.ssfm.features.play

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.part_main_play_music.rcii_album
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
import taiwan.no1.app.ssfm.features.base.AdvancedActivity
import taiwan.no1.app.ssfm.misc.utilies.devices.interfaces.IMultiMediaPlayer
import taiwan.no1.app.ssfm.misc.utilies.devices.interfaces.IPlayList
import taiwan.no1.app.ssfm.misc.utilies.devices.interfaces.IPlayerHandler
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   9/10/17
 */
class PlayMainActivity : AdvancedActivity<PlayMainViewModel, ActivityMusicBinding>() {
    @Inject override lateinit var viewModel: PlayMainViewModel
    private lateinit var mediaPlayerAdapter: IMultiMediaPlayer
    private lateinit var playList: IPlayList
    private lateinit var player: IPlayerHandler
    private val permissionsStorage: Array<String> = arrayOf(WRITE_EXTERNAL_STORAGE)
    private val path: Array<String> = arrayOf("https://soundsthatmatterblog.files.wordpress.com/2012/12/04-just-give-me-a-reason-feat-nate-ruess.mp3",
                                              "/storage/emulated/0/Download/test.mp3")
    private val permissionsRequestCode = 1

    //    private lateinit var musicPlayer: ExoPlayerWrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var durationListener: (duration: Int) -> Unit = { duration ->
            rcii_album.endTime = duration
        }

        var bufferPercentageListener: (percentage: Int) -> Unit = { percentage ->
        }

        //        musicPlayer = ExoPlayerWrapper(this.applicationContext, durationListener, bufferPercentageListener, {})

        /*mediaPlayerAdapter = MediaPlayerAdapter(rcii_album)
        mediaPlayerAdapter.setDurationListener { duration ->
            rcii_album.endTime = duration
        }
        playList = PlayListModel()
        player = PlayerHandler(mediaPlayerAdapter, playList)

        requirePermission()

        playList.setList(path.size)
        player.setPlayList(path)

        rcii_album.onClickEvent = { view, isPaused ->
            if (isPaused) {
                logi("pause")
                player.pause()
            }
            else {
                if (player.playerState() == IPlayerHandler.EPlayerState.STOP) {
                    logi("play")
                    player.random(true)
                    player.play(0)
                }
                else if (player.playerState() == IPlayerHandler.EPlayerState.PAUSE) {
                    logi("resume")
                    player.resume()
                }
            }
        }*/
    }

    private fun requirePermission() {
        ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE).takeIf {
            it == PackageManager.PERMISSION_DENIED
        }?.let {
            ActivityCompat.requestPermissions(this,
                                              permissionsStorage, permissionsRequestCode)
        }
    }

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_music)
    //endregion
}