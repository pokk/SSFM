package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTagEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTagsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase

/**
 * @author  jieyi
 * @since   10/21/17
 */
@Module
class UseCaseModule {
    // Chart top
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
    fun provideTopTagsUsecase(dataRepository: DataRepository): BaseUsecase<ChartTopTagEntity, GetTopTagsCase.RequestValue> =
        GetTopTagsCase(dataRepository)

    // Artist
    @Provides
    @PerFragment
    fun provideArtistInfoUsecase(dataRepository: DataRepository): BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue> =
        GetArtistInfoCase(dataRepository)

    @Provides
    @PerFragment
    fun provideArtistTopAlbumUsecase(dataRepository: DataRepository): BaseUsecase<ArtistTopAlbumEntity, GetTopAlbumsCase.RequestValue> =
        GetTopAlbumsCase(dataRepository)

    // Album
    @Provides
    @PerFragment
    fun provideAlbumInfoUsecase(dataRepository: DataRepository): BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue> =
        GetAlbumInfoCase(dataRepository)

    // Search music
    @Provides
    @PerFragment
    fun provideGetUsecase(dataRepository: DataRepository): BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue> =
        GetKeywordHistoriesCase(dataRepository)

    @Provides
    @PerFragment
    fun provideSearchUsecase(dataRepository: DataRepository): BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue> =
        SearchMusicCase(dataRepository)

    // For Database
    @Provides
    @PerFragment
    fun provideDeleteUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue> =
        RemoveKeywordHistoriesCase(dataRepository)
}