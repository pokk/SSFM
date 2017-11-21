package taiwan.no1.app.ssfm.models.data

import io.reactivex.Observable
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistSimilarEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagTopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackSimilarEntity
import taiwan.no1.app.ssfm.models.entities.v2.HotPlaylistEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.entities.v2.SongListEntity

/**
 * Interface that represents a data store from where data is retrieved.
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IDataStore {
    //region V1
    /**
     * Retrieve the musics or the artists information by the keyword.
     *
     * @hashCode keyword   keyword of the music or the artist.
     * @return          the result of [SearchMusicEntity]
     */
    @Deprecated("There is a better api for searching a music, please check v2")
    fun getSearchMusicRes(keyword: String,
                          page: Int = 1,
                          pageSize: Int = Constant.QUERY_PAGE_SIZE): Observable<SearchMusicEntity>

    /**
     * Retrieve the detail of a music information.
     *
     * @hashCode hash  the hash code of a music.
     * @return      the result of [DetailMusicEntity]
     */
    @Deprecated("There is a better api for searching a music, please check v2")
    fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity>
    //endregion

    //region V2
    fun searchMusic(keyword: String, page: Int = 0, lang: String = ""): Observable<MusicEntity>

    fun fetchRankMusic(rankType: Int = 0): Observable<MusicRankEntity>

    fun fetchHotPlaylist(page: Int = 0): Observable<HotPlaylistEntity>

    fun fetchPlaylistDetail(id: String = ""): Observable<SongListEntity>
    //endregion

    //region Chart
    fun getChartTopArtist(page: Int = 1, limit: Int = 20): Observable<TopArtistEntity>

    fun getChartTopTracks(page: Int = 1, limit: Int = 20): Observable<TopTrackEntity>

    fun getChartTopTags(page: Int = 1, limit: Int = 30): Observable<TopTagEntity>
    //endregion

    //region Artist
    fun getArtistInfo(mbid: String = "", artist: String = ""): Observable<ArtistEntity>

    fun getSimilarArtist(artist: String): Observable<ArtistSimilarEntity>

    fun getArtistTopAlbum(artist: String): Observable<ArtistTopAlbumEntity>

    fun getArtistTopTrack(artist: String): Observable<ArtistTopTrackEntity>

    fun getArtistTags(artist: String, session: Any): Observable<Collection<String>>

    fun getSimilarTracks(artist: String, track: String): Observable<TrackSimilarEntity>
    //endregion

    //region Album
    fun getAlbumInfo(artist: String, albumOrMbid: String): Observable<AlbumEntity>
    //endregion

    //region Tag
    fun getTagTopAlbums(tag: String = "",
                        page: Int = 1,
                        limit: Int = 20): Observable<TopAlbumEntity>

    fun getTagTopArtists(tag: String = "",
                         page: Int = 1,
                         limit: Int = 20): Observable<TagTopArtistEntity>

    fun getTagTopTracks(tag: String = "",
                        page: Int = 1,
                        limit: Int = 20): Observable<TopTrackEntity>

    fun getTagInfo(tag: String = ""): Observable<TagEntity>
    //endregion

    //region Playlist
    fun getPlaylists(id: Long = -1): Observable<List<PlaylistEntity>>

    fun addPlaylist(entity: PlaylistEntity): Observable<PlaylistEntity>

    fun editPlaylist(entity: PlaylistEntity): Observable<Boolean>

    fun removePlaylist(entity: PlaylistEntity): Observable<Boolean>

    fun getPlaylistItems(playlistId: Long = -1): Observable<List<PlaylistItemEntity>>

    fun addPlaylistItem(entity: PlaylistItemEntity): Observable<Boolean>

    fun removePlaylistItem(entity: PlaylistItemEntity): Observable<Boolean>
    //endregion

    //region Search History
    fun insertKeyword(keyword: String): Observable<Boolean>

    fun getKeywords(quantity: Int = -1): Observable<List<KeywordEntity>>

    fun removeKeywords(keyword: String? = null): Observable<Boolean>
    //endregion
}