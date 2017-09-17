package taiwan.no1.app.ssfm

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.logw
import com.raizlabs.android.dbflow.config.FlowManager
import kotlin.system.measureTimeMillis

/**
 *
 * @author  jieyi
 * @since   9/17/17
 */
class InitializeService: IntentService("InitializeService") {
    companion object {
        private const val ACTION_INIT_WHEN_APP_CREATE = "taiwan.no1.service.action.INIT"
        fun start(context: Context) {
            context.startService(Intent(context, InitializeService::class.java).apply {
                action = ACTION_INIT_WHEN_APP_CREATE
            })
//            context.startService<InitializeService>(Pair("action", ACTION_INIT_WHEN_APP_CREATE))
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        logd()
        intent?.let {
            val action = intent.action
            logw(action)
            if (ACTION_INIT_WHEN_APP_CREATE == action) {
                logw()
                initial()
            }
        }
    }

    private fun initial() {
        val time = measureTimeMillis {
            // Initial the database.
            FlowManager.init(this)
        }
        logw(time)
    }
}