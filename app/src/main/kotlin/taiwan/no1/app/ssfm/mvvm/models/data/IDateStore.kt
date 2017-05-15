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
}