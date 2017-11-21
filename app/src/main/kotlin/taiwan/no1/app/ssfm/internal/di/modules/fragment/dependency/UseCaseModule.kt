package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.DeleteSearchHistoryCase
import taiwan.no1.app.ssfm.models.usecases.EditPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.EditPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.FetchAlbumInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchArtistInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchHotPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.FetchMusicDetailCase
import taiwan.no1.app.ssfm.models.usecases.FetchMusicRankCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistDetailCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.FetchSearchHistoryCase
import taiwan.no1.app.ssfm.models.usecases.FetchTagInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopAlbumOfArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopAlbumOfTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistOfTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackOfArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackOfTagCase
import taiwan.no1.app.ssfm.models.usecases.GetAlbumInfoUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopAlbumsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopTracksUsecase
import taiwan.no1.app.ssfm.models.usecases.GetDetailMusicUsecase
import taiwan.no1.app.ssfm.models.usecases.GetKeywordHistoriesUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagInfoUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopAlbumsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopArtistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopTracksUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTopArtistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTopTagsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTopTracksUsecase
import taiwan.no1.app.ssfm.models.usecases.RemoveKeywordHistoriesUsecase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistCase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV1Case
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.GetHotPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicRankUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetSongListUsecase
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
    fun provideTopArtistsUsecase(dataRepository: DataRepository): FetchTopArtistCase =
        GetTopArtistsUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopTracksUsecase(dataRepository: DataRepository): FetchTopTrackCase = GetTopTracksUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideTopTagsUsecase(dataRepository: DataRepository): FetchTopTagCase = GetTopTagsUsecase(dataRepository)
    //endregion

    //region Artist
    @Provides
    @PerFragment
    fun provideArtistInfoUsecase(dataRepository: DataRepository): FetchArtistInfoCase =
        GetArtistInfoUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideArtistTopAlbumUsecase(dataRepository: DataRepository): FetchTopAlbumOfArtistCase =
        GetArtistTopAlbumsUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideArtistTopTrackUsecase(dataRepository: DataRepository): FetchTopTrackOfArtistCase =
        GetArtistTopTracksUsecase(dataRepository)
    //endregion

    //region Album
    @Provides
    @PerFragment
    fun provideAlbumInfoUsecase(dataRepository: DataRepository): FetchAlbumInfoCase = GetAlbumInfoUsecase(dataRepository)
    //endregion

    //region Tag
    @Provides
    @PerFragment
    fun provideTagInfoUsecase(dataRepository: DataRepository): FetchTagInfoCase = GetTagInfoUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideTagTopAlbumUsecase(dataRepository: DataRepository): FetchTopAlbumOfTagCase =
        GetTagTopAlbumsUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideTagTopArtistUsecase(dataRepository: DataRepository): FetchTopArtistOfTagCase =
        GetTagTopArtistsUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideTagTopTrackUsecase(dataRepository: DataRepository): FetchTopTrackOfTagCase =
        GetTagTopTracksUsecase(dataRepository)
    //endregion

    //region Search music V1
    @Provides
    @PerFragment
    fun provideGetKeywordHistoryUsecase(dataRepository: DataRepository): FetchSearchHistoryCase =
        GetKeywordHistoriesUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideSearchMusicUsecase(dataRepository: DataRepository): SearchMusicV1Case =
        SearchMusicUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideGetDetailMusicUsecase(dataRepository: DataRepository): FetchMusicDetailCase =
        GetDetailMusicUsecase(dataRepository)
    // endregion

    //region Search music V2
    @Provides
    @PerFragment
    fun provideSearchMusicV2Usecase(dataRepository: DataRepository): SearchMusicV2Case =
        taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideMusicRankUsecase(dataRepository: DataRepository): FetchMusicRankCase =
        GetMusicRankUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideHotPlaylistUsecase(dataRepository: DataRepository): FetchHotPlaylistCase =
        GetHotPlaylistUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideHotPlaylistDetailUsecase(dataRepository: DataRepository): FetchPlaylistDetailCase =
        GetSongListUsecase(dataRepository)
    //endregion

    //region For Database
    @Provides
    @PerFragment
    fun provideDeleteUsecase(dataRepository: DataRepository): DeleteSearchHistoryCase =
        RemoveKeywordHistoriesUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("add_playlist_item")
    fun provideAddPlaylistItemUsecase(dataRepository: DataRepository): AddPlaylistItemCase =
        AddPlaylistItemUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideAddPlaylistUsecase(dataRepository: DataRepository): AddPlaylistCase =
        AddPlaylistUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideGetPlaylistsUsecase(dataRepository: DataRepository): FetchPlaylistCase =
        GetPlaylistsUsecase(dataRepository)

    @Provides
    @PerFragment
    fun provideGetPlaylistItemsUsecase(dataRepository: DataRepository): FetchPlaylistItemCase =
        GetPlaylistItemsUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("remove_playlist")
    fun provideRemovePlaylistUsecase(dataRepository: DataRepository): RemovePlaylistCase =
        RemovePlaylistUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("remove_playlist_item")
    fun provideRemovePlaylistItemUsecase(dataRepository: DataRepository): RemovePlaylistItemCase =
        RemovePlaylistItemUsecase(dataRepository)

    @Provides
    @PerFragment
    @Named("edit_playlist")
    fun provideEditPlaylistUsecase(dataRepository: DataRepository): EditPlaylistCase =
        EditPlaylistUsecase(dataRepository)
    //endregion
}