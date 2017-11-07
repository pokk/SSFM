package taiwan.no1.app.ssfm.internal.di.modules

import android.preference.PreferenceActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.functions.chart.ChartActivity
import taiwan.no1.app.ssfm.functions.login.LoginActivity
import taiwan.no1.app.ssfm.functions.main.MainActivity
import taiwan.no1.app.ssfm.functions.main.TestActivity
import taiwan.no1.app.ssfm.functions.play.PlayMainActivity
import taiwan.no1.app.ssfm.functions.playlist.PlaylistActivity
import taiwan.no1.app.ssfm.functions.search.SearchActivity
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.ActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.ChartActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.LoginActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.MainActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.PlayMainActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.PlaylistActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.PreferenceActivityModule
import taiwan.no1.app.ssfm.internal.di.modules.activity.dependency.SearchActivityModule

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
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    abstract fun contributeMainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(LoginActivityModule::class))
    abstract fun contributeLoginActivityInjector(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PlayMainActivityModule::class))
    abstract fun contributePlayMainActivityInjector(): PlayMainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PlaylistActivityModule::class, BindingFragmentModule::class))
    abstract fun contributePlaylistActivityInjector(): PlaylistActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ChartActivityModule::class, BindingFragmentModule::class))
    abstract fun contributeChartActivityInjector(): ChartActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SearchActivityModule::class, BindingFragmentModule::class))
    abstract fun contributeSearchActivityInjector(): SearchActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PreferenceActivityModule::class))
    abstract fun contributePreferencesActivityInjector(): PreferenceActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class))
    abstract fun contributeTestActivityInjector(): TestActivity
}