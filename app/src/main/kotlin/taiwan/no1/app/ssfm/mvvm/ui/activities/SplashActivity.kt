package taiwan.no1.app.ssfm.mvvm.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler

/**
 *
 * @author  jieyi
 * @since   5/7/17
 */

class SplashActivity: Activity() {
    companion object {
        private val SPLASH_DELAY_TIME: Long = 500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delay 0.5 second for showing the splash view.
        Handler().postDelayed({
            this.let {
                // Transfer to your main activity after delay 0.5 second.
                it.startActivity(Intent(this, MainActivity::class.java))
                it.finish()
            }
        }, SPLASH_DELAY_TIME)
    }
}