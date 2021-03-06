package taiwan.no1.app.ssfm.internal.di.components

import android.content.Context
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.modules.AppModule
import taiwan.no1.app.ssfm.internal.di.modules.BindingActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.RepositoryModule
import javax.inject.Singleton

/**
 * A component whose lifetime is the life of the application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, BindingActivityModule::class, AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<App> {
    /** [AndroidInjector] Builder for using on this whole app. */
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()

    /** Providing to dependence components. */
    fun context(): Context
}