package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase.RequestValues

/**
 *
 * @author  jieyi
 * @since   8/14/17
 */
abstract class BaseUsecase<R: RequestValues>(protected val repository: IDataStore) {
    lateinit protected var parameters: R

    fun <T> execute(observer: Observer<T>): Observable<T> {
        return this.fetchUsecase().
            subscribeOn(this.obtainSubscribeScheduler()).
            observeOn(this.obtainObserverScheduler()).
            subscribe(observer)
    }

    abstract protected fun <T> fetchUsecase(): Observable<T>

    open protected fun obtainSubscribeScheduler() = Schedulers.io()

    open protected fun obtainObserverScheduler() = AndroidSchedulers.mainThread()

    interface RequestValues
}