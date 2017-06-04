package taiwan.no1.app.ssfm.mvvm.models.data.repositories

import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Session
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
class DataRepository(private val localDataStore: IDateStore, private val remoteDataStore: IDateStore): IDateStore {
    override fun obtainSession(user: String, pwd: String): Observable<Session> {
        return this.remoteDataStore.obtainSession(user, pwd)
    }

    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicModel> {
        return this.remoteDataStore.getSearchMusicRes(keyword)
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        return this.remoteDataStore.getDetailMusicRes(hash)
    }

    override fun getChartTopArtist(page: Int): Observable<Collection<Artist>> {
        return this.remoteDataStore.getChartTopArtist(page)
    }

    override fun getChartTopTracks(page: Int): Observable<Collection<Track>> {
        return this.remoteDataStore.getChartTopTracks(page)
    }

    override fun getSimilarArtist(artist: String): Observable<Collection<Artist>> {
        return this.remoteDataStore.getSimilarArtist(artist)
    }

    override fun getArtistTopAlbum(artist: String): Observable<Collection<Album>> {
        return this.remoteDataStore.getArtistTopAlbum(artist)
    }

    override fun getArtistTags(artist: String, session: Session): Observable<Collection<String>> {
        return this.remoteDataStore.getArtistTags(artist, session)
    }

    override fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>> {
        return this.remoteDataStore.getSimilarTracks(artist, mbid)
    }

    override fun getLovedTracks(user: String, page: Int): Observable<Collection<Track>> {
        return this.remoteDataStore.getLovedTracks(user, page)
    }

    override fun loveTrack(artist: String, track: String, session: Session): Observable<Track> {
        return this.remoteDataStore.loveTrack(artist, track, session)
    }

    override fun unloveTrack(artist: String, track: String, session: Session): Observable<Track> {
        return this.remoteDataStore.unloveTrack(artist, track, session)
    }
}