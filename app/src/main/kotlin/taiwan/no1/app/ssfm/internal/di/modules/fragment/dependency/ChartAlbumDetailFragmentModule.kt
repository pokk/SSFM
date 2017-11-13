package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.chart.ChartAlbumDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetAlbumInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   11/1/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartAlbumDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(albumsUsecase: BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>,
                         artistUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>):
        ChartAlbumDetailFragmentViewModel = ChartAlbumDetailFragmentViewModel(albumsUsecase,
        artistUsecase)
}