package taiwan.no1.app.ssfm.mvvm.models.data

import de.umass.lastfm.Session
import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistSimilarEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackSimilarEntity

/**
 * Interface that represents a data store from where data is retrieved.
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IDataStore {
    /**
     * Retrieve the musics or the artists information by the keyword.
     *
     * @param keyword   keyword of the music or the artist.
     * @return          the result of [SearchMusicEntity]
     */
    fun getSearchMusicRes(keyword: String,
                          page: Int = 1,
                          pageSize: Int = Constant.QUERY_PAGE_SIZE): Observable<SearchMusicEntity>

    /**
     * Retrieve the detail of a music information.
     *
     * @param hash  the hash code of a music.
     * @return      the result of [DetailMusicEntity]
     */
    fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity>

    /**
     * Retrieve the user session(This session's expired date is no limitation).
     *
     * @param user  user name.
     * @param pwd   user password.
     * @return      The user session.
     */
    // NOTE: 5/13/17 We should keep the 'Session' and 'UserName' in the shared preferences.
    fun obtainSession(user: String, pwd: String): Observable<Session>

    fun getChartTopArtist(page: Int = 1, limit: Int = 20): Observable<ChartTopArtistEntity>

    fun getChartTopTracks(page: Int = 1, limit: Int = 20): Observable<ChartTopTrackEntity>

    fun getSimilarArtist(artist: String): Observable<ArtistSimilarEntity>

    fun getArtistTopAlbum(artist: String): Observable<ArtistTopAlbumEntity>

    fun getAlbumInfo(artist: String, albumOrMbid: String): Observable<AlbumEntity>

    fun getArtistTags(artist: String, session: Session): Observable<Collection<String>>

    fun getSimilarTracks(artist: String, track: String): Observable<TrackSimilarEntity>

    fun getLovedTracks(user: String, page: Int = 1): Observable<Collection<Track>>

    fun loveTrack(artist: String, track: String, session: Session): Observable<Track>

    fun unloveTrack(artist: String, track: String, session: Session): Observable<Track>

    fun insertKeyword(keyword: String): Observable<Boolean>

    fun getKeywords(quantity: Int = -1): Observable<List<KeywordEntity>>

    fun removeKeywords(keyword: String? = null): Observable<Boolean>
}