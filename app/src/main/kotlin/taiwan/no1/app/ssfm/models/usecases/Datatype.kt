package taiwan.no1.app.ssfm.models.usecases

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
import taiwan.no1.app.ssfm.models.entities.v2.HotPlaylistEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity
import taiwan.no1.app.ssfm.models.entities.v2.SongListEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase.RequestValue
import taiwan.no1.app.ssfm.models.usecases.v2.GetHotPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetMusicRankUsecase
import taiwan.no1.app.ssfm.models.usecases.v2.GetSongListUsecase

/**
 * @author  jieyi
 * @since   11/21/17
 */
typealias FetchAlbumInfoCase = BaseUsecase<AlbumEntity, GetAlbumInfoUsecase.RequestValue>
typealias FetchArtistInfoCase = BaseUsecase<ArtistEntity, GetArtistInfoUsecase.RequestValue>
typealias FetchTopArtistCase = BaseUsecase<TopArtistEntity, GetTopArtistsUsecase.RequestValue>
typealias FetchTopTrackCase = BaseUsecase<TopTrackEntity, GetTopTracksUsecase.RequestValue>
typealias FetchTopTagCase = BaseUsecase<TopTagEntity, GetTopTagsUsecase.RequestValue>
typealias FetchTopAlbumOfArtistCase = BaseUsecase<ArtistTopAlbumEntity, GetArtistTopAlbumsUsecase.RequestValue>
typealias FetchTopTrackOfArtistCase = BaseUsecase<ArtistTopTrackEntity, GetArtistTopTracksUsecase.RequestValue>
typealias FetchTagInfoCase = BaseUsecase<TagEntity, GetTagInfoUsecase.RequestValue>
typealias FetchTopAlbumOfTagCase = BaseUsecase<TopAlbumEntity, GetTagTopAlbumsUsecase.RequestValue>
typealias FetchTopArtistOfTagCase = BaseUsecase<TagTopArtistEntity, GetTagTopArtistsUsecase.RequestValue>
typealias FetchTopTrackOfTagCase = BaseUsecase<TopTrackEntity, GetTagTopTracksUsecase.RequestValue>
typealias FetchRankChartCase = BaseUsecase<List<RankChartEntity>, BaseUsecase.RequestValues>
typealias FetchSearchHistoryCase = BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesUsecase.RequestValue>
typealias SearchMusicV1Case = BaseUsecase<SearchMusicEntity, SearchMusicUsecase.RequestValue>
typealias FetchMusicDetailCase = BaseUsecase<DetailMusicEntity, GetDetailMusicUsecase.RequestValue>
typealias SearchMusicV2Case = BaseUsecase<MusicEntity, taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase.RequestValue>
typealias FetchMusicRankCase = BaseUsecase<MusicRankEntity, GetMusicRankUsecase.RequestValue>
typealias FetchHotPlaylistCase = BaseUsecase<HotPlaylistEntity, GetHotPlaylistUsecase.RequestValue>
typealias FetchPlaylistDetailCase = BaseUsecase<SongListEntity, GetSongListUsecase.RequestValue>
typealias DeleteSearchHistoryCase = BaseUsecase<Boolean, RemoveKeywordHistoriesUsecase.RequestValue>
typealias AddPlaylistItemCase = BaseUsecase<Boolean, AddPlaylistItemUsecase.RequestValue>
typealias AddPlaylistCase = BaseUsecase<PlaylistEntity, RequestValue>
typealias FetchPlaylistCase = BaseUsecase<List<PlaylistEntity>, RequestValue>
typealias FetchPlaylistItemCase = BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>
typealias SaveSearchHistoryCase = BaseUsecase<Boolean, SaveKeywordHistoryUsecase.RequestValue>
typealias RemovePlaylistCase = BaseUsecase<Boolean, RequestValue>
typealias RemovePlaylistItemCase = BaseUsecase<Boolean, AddPlaylistItemUsecase.RequestValue>
typealias EditPlaylistCase = BaseUsecase<Boolean, RequestValue>