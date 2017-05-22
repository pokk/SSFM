package taiwan.no1.app.ssfm.mvvm.models.data.repositories

import de.umass.lastfm.Session
import io.reactivex.Observable
import taiwan.no1.app.ssfm.mvvm.models.SearchMusicModel
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore
import taiwan.no1.app.ssfm.mvvm.models.test

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

    override fun getDetailMusicRes(hash: String): Observable<test> {
        return this.remoteDataStore.getDetailMusicRes(hash)
    }
}