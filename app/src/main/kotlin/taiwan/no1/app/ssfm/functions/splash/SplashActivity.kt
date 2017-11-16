package taiwan.no1.app.ssfm.functions.splash

import android.app.Activity
import android.os.Bundle
import com.devrapid.kotlinknifer.SharedPrefs
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import org.jetbrains.anko.startActivity
import taiwan.no1.app.ssfm.functions.playlist.PlaylistActivity
import taiwan.no1.app.ssfm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import kotlin.concurrent.timer

/**
 * Welcome activity.
 *
 * @author  jieyi
 * @since   5/7/17
 */
class SplashActivity : Activity() {
    companion object {
        private val SPLASH_DELAY_TIME by lazy { 100L }
    }

    private var isFirstStartApp by SharedPrefs(false)

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // OPTIMIZE(jieyi): 11/17/17 The first active activity must init history data.
        if (!isFirstStartApp) {
            AddPlaylistUsecase(LocalDataStore()).
                execute(AddPlaylistUsecase.RequestValue(PlaylistEntity(name = "history", is_history = true))) {
                    onNext { logd("Create the history playlist!") }
                    onError { loge("Fail to create the history playlist :(") }
                    onComplete { isFirstStartApp = true }
                }
        }

        // Delay 0.5 second for showing the splash view.
        timer(initialDelay = SPLASH_DELAY_TIME, period = 1) {
            // Transfer to your main activity after delay 0.5 second.
            startActivity<PlaylistActivity>()
            finish()
            cancel()
        }
    }
    //endregion
}