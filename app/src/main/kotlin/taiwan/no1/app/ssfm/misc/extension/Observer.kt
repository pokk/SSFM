package taiwan.no1.app.ssfm.misc.extension

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * RxJava2 extension tool.
 *
 * @author  jieyi
 * @since   8/14/17
 */
fun <T> observer(): ObserverPlugin<T> = ObserverPlugin()

fun <T> observer(onError: (Throwable) -> Unit = {},
                 onComplete: () -> Unit = {},
                 onSubscribe: (Disposable) -> Unit = {},
                 onNext: (T) -> Unit = {}) =
    ObserverPlugin<T>().apply {
        onSubscribe(onSubscribe)
        onNext(onNext)
        onComplete(onComplete)
        onError(onError)
    }

class ObserverPlugin<T>: Observer<T> {
    private var _onSubscribe: ((Disposable) -> Unit)? = null
    private var _onNext: ((T) -> Unit)? = null
    private var _onComplete: (() -> Unit)? = null
    private var _onError: ((Throwable) -> Unit)? = null

    override fun onSubscribe(d: Disposable) = this._onSubscribe?.invoke(d) ?: Unit
    override fun onNext(t: T) = this._onNext?.invoke(t) ?: Unit
    override fun onComplete() = this._onComplete?.invoke() ?: Unit
    override fun onError(e: Throwable) = this._onError?.invoke(e) ?: Unit

    fun onSubscribe(onSubscribeFun: (Disposable) -> Unit): ObserverPlugin<T> = this.apply { _onSubscribe = onSubscribeFun }
    fun onNext(onNextFun: (t: T) -> Unit): ObserverPlugin<T> = this.apply { _onNext = onNextFun }
    fun onComplete(onCompleteFun: () -> Unit): ObserverPlugin<T> = this.apply { _onComplete = onCompleteFun }
    fun onError(onErrorFun: (t: Throwable) -> Unit): ObserverPlugin<T> = this.apply { _onError = onErrorFun }

    fun ObserverPlugin<T>.copy(): ObserverPlugin<T> = ObserverPlugin<T>().apply {
        // TODO(jieyi): 8/14/17 Those functions are the same pointer as before. Duplicate the functions!
        _onSubscribe = this@copy._onSubscribe
        _onNext = this@copy._onNext
        _onComplete = this@copy._onComplete
        _onError = this@copy._onError
    }
}
