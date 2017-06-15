package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.internal.di.annotations.scope.PerActivity
import taiwan.no1.app.ssfm.mvvm.ui.activities.MainActivity
import taiwan.no1.app.ssfm.mvvm.ui.activities.TestActivity

/**
 *
 * @author  jieyi
 * @since   6/13/17
 */
@Module
abstract class BindsModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class))
    abstract fun contributeTestActivityInjector(): TestActivity
}