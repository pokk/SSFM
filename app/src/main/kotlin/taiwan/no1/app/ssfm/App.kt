package taiwan.no1.app.ssfm

import android.app.Application
import android.content.Context
import taiwan.no1.app.ssfm.internal.di.components.AppComponent

/**
 * Android Main Application.
 *
 * @author  jieyi
 * @since   5/9/17
 */

class App: Application() {
    companion object {
        lateinit private var context: Context

        fun appComponent(): AppComponent = (context as App).appComponent
        // Provide the global application context.
        fun getAppContext(): Context = context
    }

    private val appComponent: AppComponent by lazy { AppComponent.Initializer.init(App@ this) }

    override fun onCreate() {
        super.onCreate()

        context = this
    }
}