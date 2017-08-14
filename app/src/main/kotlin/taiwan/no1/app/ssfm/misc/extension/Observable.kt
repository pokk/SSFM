package taiwan.no1.app.ssfm.misc.extension

import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.internal.operators.observable.ObservableCreate

/**
 * RxJava2 extension tool.
 *
 * @author  jieyi
 * @since   8/4/17
 */

inline fun <T> observable(crossinline body: (ObservableEmitter<T>) -> Unit): Observable<T> = ObservableCreate { body(it) }