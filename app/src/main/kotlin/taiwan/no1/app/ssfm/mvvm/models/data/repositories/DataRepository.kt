package taiwan.no1.app.ssfm.mvvm.models.data.repositories

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
class DataRepository(private val localDataStore: IDateStore, private val remoteDataStore: IDateStore): IDateStore {
    override fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session> {
        return this.remoteDataStore.obtainSession(user, pwd, key, secret)
    }

    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicModel> {
        return this.remoteDataStore.getSearchMusicRes(keyword)
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        return this.remoteDataStore.getDetailMusicRes(hash)
    }

    override fun getChartTopArtist(): Observable<List<Artist>> {
        return this.remoteDataStore.getChartTopArtist()
    }

    override fun getChartTopTracks(): Observable<List<Track>> {
        return this.remoteDataStore.getChartTopTracks()
    }

    override fun getSimilarArtist(): Observable<List<Artist>> {
        return this.remoteDataStore.getSimilarArtist()
    }

    override fun getArtistTopAlbum(): Observable<List<Track>> {
        return this.remoteDataStore.getArtistTopAlbum()
    }

    override fun getArtistTags(): Observable<List<Tag>> {
        return this.remoteDataStore.getArtistTags()
    }

    override fun getSimilarTracks(): Observable<List<Track>> {
        return this.remoteDataStore.getSimilarTracks()
    }

    override fun getLovedTracks(): Observable<List<Track>> {
        return this.remoteDataStore.getLovedTracks()
    }

    override fun loveTrack(): Observable<Track> {
        return this.remoteDataStore.loveTrack()
    }

    override fun unloveTrack(): Observable<Track> {
        return this.remoteDataStore.unloveTrack()
    }
}