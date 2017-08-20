package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.MainActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.TestActivity

/**
 * Dagger module that provides each of [Activity] in this central module during the activity lifecycle.
 *
 * @author  jieyi
 * @since   6/13/17
 */
@Module
abstract class BindsModule {
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, FragmentModule::class))
    abstract fun contributeMainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class))
    abstract fun contributeTestActivityInjector(): TestActivity
}