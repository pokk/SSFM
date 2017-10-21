package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartArtistDetailViewModel

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module
class ChartArtistDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideTopArtistsUsecase(dataRepository: DataRepository): BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue> =
        GetArtistInfoCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopTagsUsecase(dataRepository: DataRepository): BaseUsecase<ArtistTopAlbumEntity, GetTopAlbumsCase.RequestValue> =
        GetTopAlbumsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideViewModel(artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                         topTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetTopAlbumsCase.RequestValue>):
        FragmentChartArtistDetailViewModel = FragmentChartArtistDetailViewModel(artistsInfoUsecase, topTopAlbumsUsecase)
}