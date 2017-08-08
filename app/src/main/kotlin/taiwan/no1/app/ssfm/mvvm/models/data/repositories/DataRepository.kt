package taiwan.no1.app.ssfm.mvvm.models.data.repositories

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
class DataRepository(private var local: IDataStore, private var remote: IDataStore): IDataStore {
    override fun obtainSession(user: String, pwd: String): Observable<Session> {
        return this.remote.obtainSession(user, pwd)
    }

    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicEntity> {
        return this.remote.getSearchMusicRes(keyword)
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity> {
        return this.remote.getDetailMusicRes(hash)
    }

    override fun getChartTopArtist(page: Int): Observable<Collection<Artist>> {
        return this.remote.getChartTopArtist(page)
    }

    override fun getChartTopTracks(page: Int): Observable<Collection<Track>> {
        return this.remote.getChartTopTracks(page)
    }

    override fun getSimilarArtist(artist: String): Observable<Collection<Artist>> {
        return this.remote.getSimilarArtist(artist)
    }

    override fun getArtistTopAlbum(artist: String): Observable<Collection<Album>> {
        return this.remote.getArtistTopAlbum(artist)
    }

    override fun getArtistTags(artist: String, session: Session): Observable<Collection<String>> {
        return this.remote.getArtistTags(artist, session)
    }

    override fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>> {
        return this.remote.getSimilarTracks(artist, mbid)
    }

    override fun getLovedTracks(user: String, page: Int): Observable<Collection<Track>> {
        return this.remote.getLovedTracks(user, page)
    }

    override fun loveTrack(artist: String, track: String, session: Session): Observable<Track> {
        return this.remote.loveTrack(artist, track, session)
    }

    override fun unloveTrack(artist: String, track: String, session: Session): Observable<Track> {
        return this.remote.unloveTrack(artist, track, session)
    }
}