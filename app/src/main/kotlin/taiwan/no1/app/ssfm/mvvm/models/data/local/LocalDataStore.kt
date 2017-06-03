package taiwan.no1.app.ssfm.mvvm.models.data.local

import de.umass.lastfm.Session
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
}
