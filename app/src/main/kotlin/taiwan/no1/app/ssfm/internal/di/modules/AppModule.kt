package taiwan.no1.app.ssfm.internal.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
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
class AppModule(private val app: Application) {
    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideAppContext(): Context = app

    @Provides
    @Singleton
    fun provideSharePreferences(application: Application): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(application)
}