package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import org.jetbrains.anko.startActivity
import kotlin.concurrent.timer

/**
 * Welcome activity.
 *
 * @author  jieyi
 * @since   5/7/17
 */
class SplashActivity: Activity() {
    companion object {
        private val SPLASH_DELAY_TIME: Long = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delay 0.5 second for showing the splash view.
        timer(initialDelay = SPLASH_DELAY_TIME, period = 1) {
            // Transfer to your main activity after delay 0.5 second.
            startActivity<PlayMainActivity>()
            finish()
            this.cancel()
        }
    }
}