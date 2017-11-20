package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagTopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.EditPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.GetAlbumInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopAlbumsCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopTracksCase
import taiwan.no1.app.ssfm.models.usecases.GetDetailMusicCase
import taiwan.no1.app.ssfm.models.usecases.GetKeywordHistoriesCase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopAlbumsCase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopArtistsCase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopTracksCase
import taiwan.no1.app.ssfm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.models.usecases.GetTopTagsCase
import taiwan.no1.app.ssfm.models.usecases.GetTopTracksCase
import taiwan.no1.app.ssfm.models.usecases.RemoveKeywordHistoriesCase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicCase
import javax.inject.Named

/**
 * @author  jieyi
 * @since   10/21/17
 */
@Module
class UseCaseModule {
    //region Chart top
    @Provides
    @PerFragment
    fun provideTopArtistsUsecase(dataRepository: DataRepository): BaseUsecase<TopArtistEntity, GetTopArtistsCase.RequestValue> =
        GetTopArtistsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopTracksUsecase(dataRepository: DataRepository): BaseUsecase<TopTrackEntity, GetTopTracksCase.RequestValue> =
        GetTopTracksCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopTagsUsecase(dataRepository: DataRepository): BaseUsecase<TopTagEntity, GetTopTagsCase.RequestValue> =
        GetTopTagsCase(dataRepository)
    //endregion

    //region Artist
    @Provides
    @PerFragment
    fun provideArtistInfoUsecase(dataRepository: DataRepository): BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue> =
        GetArtistInfoCase(dataRepository)

    @Provides
    @PerFragment
    fun provideArtistTopAlbumUsecase(dataRepository: DataRepository): BaseUsecase<ArtistTopAlbumEntity, GetArtistTopAlbumsCase.RequestValue> =
        GetArtistTopAlbumsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideArtistTopTrackUsecase(dataRepository: DataRepository): BaseUsecase<ArtistTopTrackEntity, GetArtistTopTracksCase.RequestValue> =
        GetArtistTopTracksCase(dataRepository)
    //endregion

    //region Album
    @Provides
    @PerFragment
    fun provideAlbumInfoUsecase(dataRepository: DataRepository): BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue> =
        GetAlbumInfoCase(dataRepository)
    //endregion

    //region Tag
    @Provides
    @PerFragment
    fun provideTagInfoUsecase(dataRepository: DataRepository): BaseUsecase<TagEntity, GetTagInfoCase.RequestValue> =
        GetTagInfoCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTagTopAlbumUsecase(dataRepository: DataRepository): BaseUsecase<TopAlbumEntity, GetTagTopAlbumsCase.RequestValue> =
        GetTagTopAlbumsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTagTopArtistUsecase(dataRepository: DataRepository): BaseUsecase<TagTopArtistEntity, GetTagTopArtistsCase.RequestValue> =
        GetTagTopArtistsCase(dataRepository)

    @Provides
    @PerFragment
    fun provideTagTopTrackUsecase(dataRepository: DataRepository): BaseUsecase<TopTrackEntity, GetTagTopTracksCase.RequestValue> =
        GetTagTopTracksCase(dataRepository)
    //endregion

    //region Search music
    @Provides
    @PerFragment
    fun provideGetUsecase(dataRepository: DataRepository): BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue> =
        GetKeywordHistoriesCase(dataRepository)

    @Provides
    @PerFragment
    fun provideSearchUsecase(dataRepository: DataRepository): BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue> =
        SearchMusicCase(dataRepository)

    @Provides
    @PerFragment
    fun provideGetDetailMusicUsecase(dataRepository: DataRepository): BaseUsecase<DetailMusicEntity, GetDetailMusicCase.RequestValue> =
        GetDetailMusicCase(dataRepository)
    // endregion

    //region For Database
    @Provides
    @PerFragment
    fun provideDeleteUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue> =
        RemoveKeywordHistoriesCase(dataRepository)

    @Provides
    @PerFragment
    @Named("add_playlist_item")
    fun provideAddPlaylistItemUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, AddPlaylistItemUsecase.RequestValue> =
        AddPlaylistItemUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideAddPlaylistUsecase(dataRepository: DataRepository): BaseUsecase<PlaylistEntity, AddPlaylistUsecase.RequestValue> =
        AddPlaylistUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideGetPlaylistsUsecase(dataRepository: DataRepository): BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue> =
        GetPlaylistsUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideGetPlaylistItemsUsecase(dataRepository: DataRepository): BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue> =
        GetPlaylistItemsUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("remove_playlist")
    fun provideRemovePlaylistUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue> =
        RemovePlaylistUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("remove_playlist_item")
    fun provideRemovePlaylistItemUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, AddPlaylistItemUsecase.RequestValue> =
        RemovePlaylistItemUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("edit_playlist")
    fun provideEditPlaylistUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue> =
        EditPlaylistUsecase(dataRepository)
    //endregion
}