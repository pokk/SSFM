package taiwan.no1.app.ssfm.misc.extension

import io.reactivex.disposables.Disposable
import io.reactivex.internal.observers.LambdaObserver

/**
 * RxJava2 extension tool.
 *
 * @author  jieyi
 * @since   8/14/17
 */
fun <T> observer(onError: (Throwable) -> Unit = {},
                 onComplete: () -> Unit = {},
                 onSubscribe: (Disposable) -> Unit = {},
                 onNext: (T) -> Unit = {}) = LambdaObserver(onNext, onError, onComplete, onSubscribe)