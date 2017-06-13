package taiwan.no1.app.ssfm.internal.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 *
 * @author  jieyi
 * @since   5/9/17
 */
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplication(app: Application): Application = app

    @Provides
    @Singleton
    fun provideAppContext(app: Application): Context = app.applicationContext
}