package taiwan.no1.app.ssfm.models.data.repositories

import io.reactivex.Observable
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Local
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Remote
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * For retrieving the data from the repository of [local] or [remote].
 *
 * @author  jieyi
 * @since   5/10/17
 */
@Singleton
class DataRepository @Inject constructor(@Local private var local: IDataStore,
                                         @Remote private var remote: IDataStore) : IDataStore {
    override fun getSearchMusicRes(keyword: String, page: Int, pageSize: Int) =
        remote.getSearchMusicRes(keyword, page, pageSize)

    override fun getDetailMusicRes(hash: String) = remote.getDetailMusicRes(hash)

    override fun searchMusic(keyword: String, page: Int, lang: String) = remote.searchMusic(keyword, page, lang)

    override fun fetchRankMusic(rankType: Int) = remote.fetchRankMusic(rankType)

    override fun fetchHotPlaylist(page: Int) = remote.fetchHotPlaylist(page)

    override fun fetchPlaylistDetail(id: String) = remote.fetchPlaylistDetail(id)

    override fun getChartTopArtist(page: Int, limit: Int) = remote.getChartTopArtist(page, limit)

    override fun getChartTopTracks(page: Int, limit: Int) = remote.getChartTopTracks(page, limit)

    override fun getChartTopTags(page: Int, limit: Int) =
        remote.getChartTopTags(page, limit)

    override fun getArtistInfo(mbid: String, artist: String) =
        remote.getArtistInfo(mbid, artist)

    override fun getSimilarArtist(artist: String) = remote.getSimilarArtist(artist)

    override fun getArtistTopAlbum(artist: String) = remote.getArtistTopAlbum(artist)

    override fun getArtistTopTrack(artist: String) = remote.getArtistTopTrack(artist)

    override fun getAlbumInfo(artist: String, albumOrMbid: String) = remote.getAlbumInfo(artist,
        albumOrMbid)

    override fun getArtistTags(artist: String, session: Any) = remote.getArtistTags(artist, session)

    override fun getSimilarTracks(artist: String, track: String) = remote.getSimilarTracks(artist,
        track)

    override fun getTagTopAlbums(tag: String, page: Int, limit: Int) = remote.getTagTopAlbums(tag,
        page,
        limit)

    override fun getTagTopArtists(tag: String, page: Int, limit: Int) = remote.getTagTopArtists(tag,
        page,
        limit)

    override fun getTagTopTracks(tag: String, page: Int, limit: Int) = remote.getTagTopTracks(tag,
        page,
        limit)

    override fun getTagInfo(tag: String) = remote.getTagInfo(tag)

    override fun getPlaylists(id: Long) = local.getPlaylists(id)

    override fun addPlaylist(entity: PlaylistEntity): Observable<PlaylistEntity> = local.addPlaylist(entity)

    override fun editPlaylist(entity: PlaylistEntity) = local.editPlaylist(entity)

    override fun removePlaylist(entity: PlaylistEntity) = local.removePlaylist(entity)

    override fun getPlaylistItems(playlistId: Long) =
        local.getPlaylistItems(playlistId)

    override fun addPlaylistItem(entity: PlaylistItemEntity) = local.addPlaylistItem(entity)

    override fun removePlaylistItem(entity: PlaylistItemEntity) = local.removePlaylistItem(entity)

    override fun insertKeyword(keyword: String) = local.insertKeyword(keyword)

    override fun getKeywords(quantity: Int) = local.getKeywords(quantity)

    override fun removeKeywords(keyword: String?) = local.removeKeywords(keyword)
}