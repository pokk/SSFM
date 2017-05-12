package taiwan.no1.app.ssfm.mvvm.models.data.remote

import de.umass.lastfm.Authenticator
import de.umass.lastfm.Session
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
class RemoteDataStore: IDateStore {
    override fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session> =
            Observable.create(ObservableOnSubscribe<Session> {
                val session = Authenticator.getMobileSession(user, pwd, key, secret)
                it.onNext(session)
                it.onComplete()
            }).subscribeOn(Schedulers.io())
}