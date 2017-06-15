package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.modules.AppModule
import taiwan.no1.app.ssfm.internal.di.modules.BindsModule
import javax.inject.Singleton

/**
 * A component whose lifetime is the life of the application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
@Singleton
@Component(modules = arrayOf(AppModule::class,
    BindsModule::class,
    AndroidSupportInjectionModule::class))
interface AppComponent: AndroidInjector<App> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<App>()
}