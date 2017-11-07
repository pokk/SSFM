package taiwan.no1.app.ssfm.mvvm.models.data.repositories

import io.reactivex.Observable
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Local
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Remote
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity
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
                                         @Remote private var remote: IDataStore): IDataStore {
    override fun obtainSession(user: String, pwd: String) = remote.obtainSession(user, pwd)

    override fun getSearchMusicRes(keyword: String, page: Int, pageSize: Int) =
        remote.getSearchMusicRes(keyword, page, pageSize)

    override fun getDetailMusicRes(hash: String) = remote.getDetailMusicRes(hash)

    override fun getChartTopArtist(page: Int, limit: Int) = remote.getChartTopArtist(page, limit)

    override fun getChartTopTracks(page: Int, limit: Int) = remote.getChartTopTracks(page, limit)

    override fun getChartTopTags(page: Int, limit: Int) =
        remote.getChartTopTags(page, limit)

    override fun getArtistInfo(mbid: String, artist: String) =
        remote.getArtistInfo(mbid, artist)

    override fun getSimilarArtist(artist: String) = remote.getSimilarArtist(artist)

    override fun getArtistTopAlbum(artist: String) = remote.getArtistTopAlbum(artist)

    override fun getArtistTopTrack(artist: String) = remote.getArtistTopTrack(artist)

    override fun getAlbumInfo(artist: String, albumOrMbid: String) = remote.getAlbumInfo(artist, albumOrMbid)

    override fun getArtistTags(artist: String, session: Any) = remote.getArtistTags(artist, session)

    override fun getSimilarTracks(artist: String, track: String) = remote.getSimilarTracks(artist, track)

    override fun getTagTopAlbums(tag: String, page: Int, limit: Int) = remote.getTagTopAlbums(tag, page, limit)

    override fun getTagTopArtists(tag: String, page: Int, limit: Int) = remote.getTagTopArtists(tag, page, limit)

    override fun getTagTopTracks(tag: String, page: Int, limit: Int) = remote.getTagTopTracks(tag, page, limit)

    override fun getTagInfo(tag: String): Observable<TagEntity> = remote.getTagInfo(tag)

    override fun getLovedTracks(user: String, page: Int) = remote.getLovedTracks(user, page)

    override fun loveTrack(artist: String, track: String, session: Any) = remote.loveTrack(artist, track, session)

    override fun unloveTrack(artist: String, track: String, session: Any) = remote.unloveTrack(artist, track, session)

    override fun insertKeyword(keyword: String) = local.insertKeyword(keyword)

    override fun getKeywords(quantity: Int) = local.getKeywords(quantity)

    override fun removeKeywords(keyword: String?) = local.removeKeywords(keyword)
}