package taiwan.no1.app.ssfm.internal.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.App
import javax.inject.Singleton

/**
 * Dagger module that provides [Context] and [Application] which will live during the application lifecycle.
 *
 * @author  jieyi
 * @since   5/9/17
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(app: App): Application = app

    @Provides
    @Singleton
    fun provideAppContext(app: App): Context = app.applicationContext
}