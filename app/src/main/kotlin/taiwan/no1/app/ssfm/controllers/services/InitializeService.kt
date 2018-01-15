package taiwan.no1.app.ssfm.controllers.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.devrapid.kotlinknifer.SharedPrefs
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import org.jetbrains.anko.defaultSharedPreferences
import taiwan.no1.app.ssfm.features.base.FirstInitFlow
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playlistManager
import weian.cheng.mediaplayerwithexoplayer.ExoPlayerWrapper

/**
 * Initialization service for optimizing the cold starting time.
 *
 * @author  jieyi
 * @since   9/17/17
 */
class InitializeService : IntentService("InitializeService") {
    companion object {
        private const val ACTION_INIT_WHEN_APP_CREATE = "taiwan.no1.service.action.INIT"
        fun start(context: Context) {
            context.startService(Intent(context, InitializeService::class.java).
                apply { action = ACTION_INIT_WHEN_APP_CREATE })
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let { if (ACTION_INIT_WHEN_APP_CREATE == intent.action) initial() }
    }

    private fun initial() {
        // Initial the shared preferences.
        SharedPrefs.setPrefSettings(defaultSharedPreferences)
        // Initial the database.
        FlowManager.init(FlowConfig.Builder(this).build())
        // Initial the player helper.
        playerHelper.hold(ExoPlayerWrapper(this.applicationContext), playlistManager)

        // OPTIMIZE(jieyi): 11/24/17 Temporally put init flow here, this should be in the first activity.
        FirstInitFlow().init()
    }
}