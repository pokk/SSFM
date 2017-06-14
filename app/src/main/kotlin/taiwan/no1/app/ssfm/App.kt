package taiwan.no1.app.ssfm

import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Android Main Application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
class App: DaggerApplication() {
    companion object {
        lateinit private var context: Context

        // Provide the global application context.
        fun getAppContext(): Context = context
    }

    init {
        context = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)
}