package taiwan.no1.app.ssfm.misc.extension

import io.reactivex.Observable
import io.reactivex.ObservableEmitter

/**
 *
 * @author  jieyi
 * @since   8/4/17
 */

inline fun <T> observable(crossinline body: (ObservableEmitter<T>) -> Unit): Observable<T> = Observable.create { body(it) }