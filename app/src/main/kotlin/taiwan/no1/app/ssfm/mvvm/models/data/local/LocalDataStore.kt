package taiwan.no1.app.ssfm.mvvm.models.data.local

import com.raizlabs.android.dbflow.kotlinextensions.`is`
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.limit
import com.raizlabs.android.dbflow.kotlinextensions.orderBy
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Session
import de.umass.lastfm.Track
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity_Table
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity


/**
 * Retrieving the data from local storage. All return objects are [Observable] to viewmodels.
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LocalDataStore: IDataStore {
    override fun getSearchMusicRes(keyword: String, page: Int, pageSize: Int): Observable<SearchMusicEntity> {
        TODO("Local database has no session...")
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity> {
        TODO("Local database has no session...")
    }

    override fun obtainSession(user: String, pwd: String): Observable<Session> {
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

    override fun insertKeyword(keyword: String) =
        (select from KeywordEntity::class where (KeywordEntity_Table.keyword `is` keyword)).rx().list.
            flatMapObservable {
                (if (0 == it.size) KeywordEntity(keyword = keyword) else it.first().apply { searchTimes += 1 }).
                    save().
                    toObservable()
            }

    override fun getKeywords(quantity: Int): Observable<List<KeywordEntity>> {
        val maxLimit = quantity.takeIf { 0 < it } ?: Int.MAX_VALUE
        return (select from KeywordEntity::class orderBy KeywordEntity_Table.searchTimes.desc() limit maxLimit).rx().list.toObservable()
    }

    override fun removeKeywords(): Observable<Boolean> = TODO()
//        delete(KeywordEntity::class).rx().execute().toObservable()
}
