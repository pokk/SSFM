package taiwan.no1.app.ssfm

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import taiwan.no1.app.ssfm.internal.di.components.DaggerAppComponent

/**
 * Android Main Application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
class App: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<App> = DaggerAppComponent.builder().create(this)
}