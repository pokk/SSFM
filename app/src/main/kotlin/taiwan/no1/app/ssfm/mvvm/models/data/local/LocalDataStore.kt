package taiwan.no1.app.ssfm.mvvm.models.data.local

import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Session
import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LocalDataStore: IDataStore {
    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicEntity> {
        /* no-op */
        TODO("Local database has no session...")
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity> {
        /* no-op */
        TODO("Local database has no session...")
    }

    override fun obtainSession(user: String, pwd: String): Observable<Session> {
        /* no-op */
        TODO("Local database has no session...")
    }

    override fun getChartTopArtist(page: Int): Observable<Collection<Artist>> {
        TODO()
    }

    override fun getChartTopTracks(page: Int): Observable<Collection<Track>> {
        TODO()
    }

    override fun getSimilarArtist(artist: String): Observable<Collection<Artist>> {
        TODO()
    }

    override fun getArtistTopAlbum(artist: String): Observable<Collection<Album>> {
        TODO()
    }

    override fun getArtistTags(artist: String, session: Session): Observable<Collection<String>> {
        TODO()
    }

    override fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>> {
        TODO()
    }

    override fun getLovedTracks(user: String, page: Int): Observable<Collection<Track>> {
        TODO()
    }

    override fun loveTrack(artist: String, track: String, session: Session): Observable<Track> {
        TODO()
    }

    override fun unloveTrack(artist: String, track: String, session: Session): Observable<Track> {
        TODO()
    }
}
