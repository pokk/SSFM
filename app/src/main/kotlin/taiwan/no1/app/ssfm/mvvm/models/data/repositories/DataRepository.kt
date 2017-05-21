package taiwan.no1.app.ssfm.mvvm.models.data.repositories

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
class DataRepository(private val localDataStore: IDateStore, private val remoteDataStore: IDateStore): IDateStore {
    override fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session> {
        return this.remoteDataStore.obtainSession(user, pwd, key, secret)
    }

    override fun getSearchMusicRes(): Observable<SearchMusicModel> {
        return this.remoteDataStore.getSearchMusicRes()
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        return this.remoteDataStore.getDetailMusicRes("test")
    }
}