package taiwan.no1.app.ssfm.internal.di.components

import android.content.Context
import dagger.Component
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.modules.AppModule
import javax.inject.Singleton

/**
 * A component whose lifetime is the life of the application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    object Initializer {
        fun init(app: App): AppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(app))
                .build()
    }

    // Exposed to sub-graphs.
    fun context(): Context
}