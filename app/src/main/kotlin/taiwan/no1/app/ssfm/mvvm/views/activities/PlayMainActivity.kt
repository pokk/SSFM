package taiwan.no1.app.ssfm.mvvm.views.activities

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import kotlinx.android.synthetic.main.part_main_play_music.rcii_album
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityMusicBinding
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
    private val mediaPlayerProxy = MediaPlayerProxy()
    private val playList = PlayListModel()
    private val player = PlayerHandler(mediaPlayerProxy, playList)
    private val permissionsStorage: Array<String> = arrayOf(WRITE_EXTERNAL_STORAGE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requirePermission()
    }

    private fun requirePermission() {
        ActivityCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE).takeIf {
            it == PackageManager.PERMISSION_DENIED
        }?.let {
            ActivityCompat.requestPermissions(this, permissionsStorage, 1)
        }
    }

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_music)
    //endregion
}