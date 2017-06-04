package taiwan.no1.app.ssfm.mvvm.models.data

import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Session
import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.DetailMusicModel
import taiwan.no1.app.ssfm.mvvm.models.SearchMusicModel

/**
 * Interface that represents a data store from where data is retrieved.
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IDateStore {
    /**
     * Retrieve the musics or the artists information by the keyword.
     *
     * @param keyword keyword of the music or the artist.
     * @return the result of [SearchMusicModel]
     */
    fun getSearchMusicRes(keyword: String): Observable<SearchMusicModel>

    /**
     * Retrieve the detail of a music information.
     *
     * @param hash the hash code of a music.
     * @return the result of [DetailMusicModel]
     */
    fun getDetailMusicRes(hash: String): Observable<DetailMusicModel>

    /**
     * Retrieve the user session(This session's expired date is no limitation).
     *
     * @param user user name.
     * @param pwd user password.
     * @param key api key.
     * @param secret
     * @return The user session.
     */
    // NOTE: 5/13/17 We should keep the 'Session' and 'UserName' in the shared preferences.
    fun obtainSession(user: String, pwd: String): Observable<Session>

    fun getChartTopArtist(page: Int = 1): Observable<Collection<Artist>>

    fun getChartTopTracks(page: Int = 1): Observable<Collection<Track>>

    fun getSimilarArtist(artist: String): Observable<Collection<Artist>>

    fun getArtistTopAlbum(artist: String): Observable<Collection<Album>>

    fun getArtistTags(artist: String, session: Session): Observable<Collection<String>>

    fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>>

    fun getLovedTracks(user: String, page: Int = 1): Observable<Collection<Track>>

    fun loveTrack(artist: String, track: String, session: Session): Observable<Track>

    fun unloveTrack(artist: String, track: String, session: Session): Observable<Track>
}