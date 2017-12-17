package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.features.chart.ChartAlbumDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.FetchAlbumInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchArtistInfoCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   11/1/17
 */
@Module(includes = [UseCaseModule::class])
class ChartAlbumDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(albumsUsecase: FetchAlbumInfoCase, artistUsecase: FetchArtistInfoCase) =
        ChartAlbumDetailFragmentViewModel(albumsUsecase, artistUsecase)
}