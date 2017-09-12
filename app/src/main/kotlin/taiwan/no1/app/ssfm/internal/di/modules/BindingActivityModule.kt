package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.IndexActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.MainActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.PlayMainActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ActivityModule
import taiwan.no1.app.ssfm.mvvm.views.activities.IndexActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.MainActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PlayMainActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PreferenceActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.TestActivity

/**
 * Dagger module that provides each of [Activity] in this central module during the activity lifecycle.
 *
 * @author  jieyi
 * @since   6/13/17
 */
@Module
abstract class BindingActivityModule {
    /**
     * ContributesAndroidInjector is including fragment injector. Only putting FragmentBindModule in modules array,
     * the children fragment can obtain the parent's fragment injector.
     */
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class, BindingFragmentModule::class))
    abstract fun contributeMainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(IndexActivityModule::class))
    abstract fun contributeIndexActivityInjector(): IndexActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PlayMainActivityModule::class))
    abstract fun contributePlayMainActivityInjector(): PlayMainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributePreferencesActivityInjector(): PreferenceActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class))
    abstract fun contributeTestActivityInjector(): TestActivity
}