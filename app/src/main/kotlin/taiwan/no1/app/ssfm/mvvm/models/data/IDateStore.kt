package taiwan.no1.app.ssfm.mvvm.models.data

import de.umass.lastfm.Artist
import de.umass.lastfm.Session
import de.umass.lastfm.Tag
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
     * Retrieve the user session(This session's expired date is no limitation).
     *
     * @param user user name.
     * @param pwd user password.
     * @param key api key.
     * @param secret
     * @return The user session.
     */
    // NOTE: 5/13/17 We should keep the 'Session' and 'UserName' in the shared preferences.
    fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session>

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

    fun getChartTopArtist(): Observable<List<Artist>>

    fun getChartTopTracks(): Observable<List<Track>>

    fun getSimilarArtist(): Observable<List<Artist>>

    fun getArtistTopAlbum(): Observable<List<Track>>

    fun getArtistTags(): Observable<List<Tag>>

    fun getSimilarTracks(): Observable<List<Track>>

    fun 
}