package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import de.umass.lastfm.Album
import de.umass.lastfm.Image
import de.umass.lastfm.PaginatedResult
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistImagesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchIndexViewModel

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module
class SearchIndexFragmentModule {
    @Provides
    @PerFragment
    fun provideTopArtistsUsecase(dataRepository: DataRepository): BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue> =
        GetTopArtistsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopTracksUsecase(dataRepository: DataRepository): BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue> =
        GetTopTracksCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopAlbumsUsecase(dataRepository: DataRepository): BaseUsecase<Collection<Album>, GetTopAlbumsCase.RequestValue> =
        GetTopAlbumsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideArtistImagesUsecase(dataRepository: DataRepository): BaseUsecase<PaginatedResult<Image>, GetArtistImagesCase.RequestValue> =
        GetArtistImagesCase(dataRepository)

    @Provides
    @PerFragment
    fun provideAlbumInfoUsecase(dataRepository: DataRepository): BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue> =
        GetAlbumInfoCase(dataRepository)

    @Provides
    @PerFragment
    fun provideViewModel(topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>,
                         topTracksUsecase: BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue>,
                         topAlbumsUsecase: BaseUsecase<Collection<Album>, GetTopAlbumsCase.RequestValue>,
                         topArtistImagesUsecase: BaseUsecase<PaginatedResult<Image>, GetArtistImagesCase.RequestValue>):
        FragmentSearchIndexViewModel = FragmentSearchIndexViewModel(topArtistsUsecase,
        topTracksUsecase,
        topAlbumsUsecase,
        topArtistImagesUsecase)
}