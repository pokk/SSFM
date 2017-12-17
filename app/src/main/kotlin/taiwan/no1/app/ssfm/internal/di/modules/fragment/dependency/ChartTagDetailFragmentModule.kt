package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.features.chart.ChartTagDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.FetchTagInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopAlbumOfTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistOfTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackOfTagCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   10/26/17
 */
@Module(includes = [UseCaseModule::class])
class ChartTagDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(tagInfoUsecase: FetchTagInfoCase,
                         topAlbumsUsecase: FetchTopAlbumOfTagCase,
                         topArtistsUsecase: FetchTopArtistOfTagCase,
                         topTracksUsecase: FetchTopTrackOfTagCase) =
        ChartTagDetailFragmentViewModel(tagInfoUsecase, topAlbumsUsecase, topArtistsUsecase, topTracksUsecase)
}