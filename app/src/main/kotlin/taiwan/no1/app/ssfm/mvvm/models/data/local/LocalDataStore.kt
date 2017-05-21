package taiwan.no1.app.ssfm.mvvm.models.data.local

import de.umass.lastfm.Session
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.SearchMusic
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LocalDataStore: IDateStore {
    override fun getSearchMusicRes(): Observable<SearchMusic> {
        /* no-op */
        throw Exception("Local database has no session...")
    }

    override fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session> {
        /* no-op */
        throw Exception("Local database has no session...")
    }
}