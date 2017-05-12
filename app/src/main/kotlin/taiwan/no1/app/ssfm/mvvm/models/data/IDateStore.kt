package taiwan.no1.app.ssfm.mvvm.models.data

import de.umass.lastfm.Session
import io.reactivex.Observable

/**
 * Interface that represents a data store from where data is retrieved.
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IDateStore {
    fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session>
}