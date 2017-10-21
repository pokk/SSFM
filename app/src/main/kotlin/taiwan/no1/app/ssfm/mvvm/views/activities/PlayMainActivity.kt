package taiwan.no1.app.ssfm.mvvm.views.activities

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.devrapid.kotlinknifer.logi
import kotlinx.android.synthetic.main.part_main_play_music.rcii_album
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
import taiwan.no1.app.ssfm.misc.utilies.devices.IMultiMediaPlayer
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayList
import taiwan.no1.app.ssfm.misc.utilies.devices.IPlayerHandler
import taiwan.no1.app.ssfm.misc.utilies.devices.MediaPlayerProxy
import taiwan.no1.app.ssfm.misc.utilies.devices.PlayListModel
import taiwan.no1.app.ssfm.misc.utilies.devices.PlayerHandler
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
    private lateinit var mediaPlayerProxy: IMultiMediaPlayer
    private lateinit var playList: IPlayList
    private lateinit var player: IPlayerHandler
    private val permissionsStorage: Array<String> = arrayOf(WRITE_EXTERNAL_STORAGE)
    private val path: Array<String> = arrayOf("http://fs.w.kugou.com/201710211733/3476653fc2fc99dcfe6345519f02f736/G100/M02/1F/03/pA0DAFjzVJyAAhJ0ADOhTT8_t00044.mp3",
        "/storage/emulated/0/Download/test.mp3")
    private val permissionsRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaPlayerProxy = MediaPlayerProxy(rcii_album)
        playList = PlayListModel()
        player = PlayerHandler(mediaPlayerProxy, playList)

        requirePermission()

        playList.setList(path.size)
        player.setPlayList(path)

        rcii_album.onClickEvent = {
            view, isPaused ->
            if (isPaused) {
                logi("pause")
                player.pause()
            } else {
                if (player.playerState() == IPlayerHandler.EPlayerState.STOP) {
                    logi("play")
                    player.random(true)
                    player.play(0)
                } else if (player.playerState() == IPlayerHandler.EPlayerState.PAUSE) {
                    logi("resume")
                    player.resume()
                }
            }
        }
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