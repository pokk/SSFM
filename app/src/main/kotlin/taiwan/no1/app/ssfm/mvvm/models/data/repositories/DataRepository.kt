package taiwan.no1.app.ssfm.mvvm.models.data.repositories

import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Session
import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Local
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Remote
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
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
    override fun obtainSession(user: String, pwd: String): Observable<Session> = remote.obtainSession(user, pwd)

    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicEntity> = remote.getSearchMusicRes(keyword)

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity> = remote.getDetailMusicRes(hash)

    override fun getChartTopArtist(page: Int): Observable<Collection<Artist>> = remote.getChartTopArtist(page)

    override fun getChartTopTracks(page: Int): Observable<Collection<Track>> = remote.getChartTopTracks(page)

    override fun getSimilarArtist(artist: String): Observable<Collection<Artist>> = remote.getSimilarArtist(artist)

    override fun getArtistTopAlbum(artist: String): Observable<Collection<Album>> = remote.getArtistTopAlbum(artist)

    override fun getArtistTags(artist: String, session: Session): Observable<Collection<String>> =
        remote.getArtistTags(artist, session)

    override fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>> =
        remote.getSimilarTracks(artist, mbid)

    override fun getLovedTracks(user: String, page: Int): Observable<Collection<Track>> =
        remote.getLovedTracks(user, page)

    override fun loveTrack(artist: String, track: String, session: Session): Observable<Track> =
        remote.loveTrack(artist, track, session)

    override fun unloveTrack(artist: String, track: String, session: Session): Observable<Track> =
        remote.unloveTrack(artist, track, session)
}