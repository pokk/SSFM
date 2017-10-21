package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartArtistDetailFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartIndexFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.CheckModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.SearchHistoriesFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.SearchIndexFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.SearchMusicFragmentModule
import taiwan.no1.app.ssfm.mvvm.views.fragments.ChartArtistDetailFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.ChartIndexFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.MainFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchHistoryFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchIndexFragment
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchResultFragment

/**
 * A base component upon which [Fragment] components may depend. Fragment-level components should extend this component.
 * Lifecycle is shorter than [PerActivity].
 *
 * @author  jieyi
 * @since   5/11/17
 */
@Module
abstract class BindingFragmentModule {
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(CheckModule::class))
    abstract fun contributeMainFragmentInjector(): MainFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SearchIndexFragmentModule::class))
    abstract fun contributeSearchIndexFragmentInjector(): SearchIndexFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SearchHistoriesFragmentModule::class))
    abstract fun contributeSearchHistoryFragmentInjector(): SearchHistoryFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SearchMusicFragmentModule::class))
    abstract fun contributeSearchResuletFragmentInjector(): SearchResultFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(ChartIndexFragmentModule::class))
    abstract fun contributeChartIndexFragmentInjector(): ChartIndexFragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(ChartArtistDetailFragmentModule::class))
    abstract fun contributeChartArtistDetailFragmentInjector(): ChartArtistDetailFragment
}