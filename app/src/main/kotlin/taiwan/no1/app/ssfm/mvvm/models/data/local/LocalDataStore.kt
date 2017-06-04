package taiwan.no1.app.ssfm.mvvm.models.data.local

import de.umass.lastfm.Artist
import de.umass.lastfm.Session
import de.umass.lastfm.Tag
import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.DetailMusicModel
import taiwan.no1.app.ssfm.mvvm.models.SearchMusicModel
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LocalDataStore: IDateStore {
    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicModel> {
        /* no-op */
        TODO("Local database has no session...")
    }

    override fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session> {
        /* no-op */
        TODO("Local database has no session...")
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        /* no-op */
        TODO("Local database has no session...")
    }

    override fun getChartTopArtist(): Observable<List<Artist>> {
        TODO()
    }

    override fun getChartTopTracks(): Observable<List<Track>> {
        TODO()
    }

    override fun getSimilarArtist(): Observable<List<Artist>> {
        TODO()
    }

    override fun getArtistTopAlbum(): Observable<List<Track>> {
        TODO()
    }

    override fun getArtistTags(): Observable<List<Tag>> {
        TODO()
    }

    override fun getSimilarTracks(): Observable<List<Track>> {
        TODO()
    }

    override fun getLovedTracks(): Observable<List<Track>> {
        TODO()
    }

    override fun loveTrack(): Observable<Track> {
        TODO()
    }

    override fun unloveTrack(): Observable<Track> {
        TODO()
    }
}
