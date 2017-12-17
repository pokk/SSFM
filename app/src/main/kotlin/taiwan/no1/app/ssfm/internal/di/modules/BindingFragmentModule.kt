package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.features.chart.ChartAlbumDetailFragment
import taiwan.no1.app.ssfm.features.chart.ChartArtistDetailFragment
import taiwan.no1.app.ssfm.features.chart.ChartIndexFragment
import taiwan.no1.app.ssfm.features.chart.ChartRankChartDetailFragment
import taiwan.no1.app.ssfm.features.chart.ChartTagDetailFragment
import taiwan.no1.app.ssfm.features.playlist.PlaylistDetailFragment
import taiwan.no1.app.ssfm.features.playlist.PlaylistIndexFragment
import taiwan.no1.app.ssfm.features.search.SearchHistoryFragment
import taiwan.no1.app.ssfm.features.search.SearchIndexFragment
import taiwan.no1.app.ssfm.features.search.SearchResultFragment
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartAlbumDetailFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartArtistDetailFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartIndexFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartRankChartDetailFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.ChartTagDetailFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.PlaylistDetailFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.PlaylistIndexFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.SearchHistoriesFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.SearchIndexFragmentModule
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.SearchMusicFragmentModule

/**
 * A base component upon which [Fragment] components may depend. Fragment-level components should extend this component.
 * Lifecycle is shorter than [PerActivity].
 *
 * @author  jieyi
 * @since   5/11/17
 */
@Module
abstract class BindingFragmentModule {
    //region Playlist
    @PerFragment
    @ContributesAndroidInjector(modules = [PlaylistIndexFragmentModule::class])
    abstract fun contributePlaylistIndexFragmentInjector(): PlaylistIndexFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [PlaylistDetailFragmentModule::class])
    abstract fun contributePlaylistDetailFragmentInjector(): PlaylistDetailFragment
    //endregion

    //region Search
    @PerFragment
    @ContributesAndroidInjector(modules = [SearchIndexFragmentModule::class])
    abstract fun contributeSearchIndexFragmentInjector(): SearchIndexFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchHistoriesFragmentModule::class])
    abstract fun contributeSearchHistoryFragmentInjector(): SearchHistoryFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [SearchMusicFragmentModule::class])
    abstract fun contributeSearchResuletFragmentInjector(): SearchResultFragment
    //endregion

    //region Chart
    @PerFragment
    @ContributesAndroidInjector(modules = [ChartIndexFragmentModule::class])
    abstract fun contributeChartIndexFragmentInjector(): ChartIndexFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ChartRankChartDetailFragmentModule::class])
    abstract fun contributeChartRankChartFragmentInjector(): ChartRankChartDetailFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ChartArtistDetailFragmentModule::class])
    abstract fun contributeChartArtistDetailFragmentInjector(): ChartArtistDetailFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ChartTagDetailFragmentModule::class])
    abstract fun contributeChartTagDetailFragmentInjector(): ChartTagDetailFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [ChartAlbumDetailFragmentModule::class])
    abstract fun contributeChartAlbumDetailFragmentInjector(): ChartAlbumDetailFragment
    //endregion
}
