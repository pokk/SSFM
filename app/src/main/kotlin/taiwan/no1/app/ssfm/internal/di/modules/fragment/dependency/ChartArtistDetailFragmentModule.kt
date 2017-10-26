package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartArtistDetailViewModel

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartArtistDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                         topTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetArtistTopAlbumsCase.RequestValue>):
        FragmentChartArtistDetailViewModel = FragmentChartArtistDetailViewModel(artistsInfoUsecase, topTopAlbumsUsecase)
}