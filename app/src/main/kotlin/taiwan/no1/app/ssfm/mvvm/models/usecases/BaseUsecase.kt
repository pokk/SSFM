package taiwan.no1.app.ssfm.mvvm.models.usecases

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase.RequestValues

/**
 * Abstract class for a Use Case as an inter-actor.
 * This interface represents a execution unit for different use cases (this means any use case in the
 * application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a [Observer] that will execute its
 * job in a background thread and will post the result in the UI thread.
 * <p>
 * For passing the request parameters [RequestValues] to repository model that set a generic type for wrapping
 * vary data.
 *
 * @author  jieyi
 * @since   8/14/17
 */
abstract class BaseUsecase<T, R: RequestValues>(protected val repository: IDataStore) {
    /** Provide a common parameter variable for the children class. */
    var parameters: R? = null

    /**
     * Executes the current use case.
     *
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(observer: Observer<T>) = this.buildUsecase().subscribe(observer)

    /**
     * Executes the current use case with request parameters.
     *
     * @param parameter the parameter for retrieving data.
     * @param observer  a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(parameter: R, observer: Observer<T>) {
        this.parameters = parameter
        this.buildUsecase().subscribe(observer)
    }

    /**
     * Choose a method from [IDataStore] and fit this usecase for return some data.
     *
     * @return an [Observer] for chaining on working threads.
     */
    abstract protected fun fetchUsecase(): Observable<T>

    // TODO(jieyi): 8/14/17 This two functions could be a interface.
    /**
     * Obtain a thread for while [Observable] is doing their tasks.
     *
     * @return [Scheduler] implement from.
     */
    open protected fun obtainSubscribeScheduler() = Schedulers.io()

    /**
     * Obtain a thread for while [Observable] is doing their tasks.
     *
     * @return [Scheduler] implement from.
     */
    open protected fun obtainObserverScheduler(): Scheduler = AndroidSchedulers.mainThread()

    /**
     * Builds an [Observable] which will be used when executing the current [BaseUsecase].
     *
     * @return [Observable] for connecting with a [Observer].
     */
    private fun buildUsecase(): Observable<T> = this.fetchUsecase().
        subscribeOn(this.obtainSubscribeScheduler()).
        observeOn(this.obtainObserverScheduler())

    /** Interface for wrap a data for passing to a request.*/
    interface RequestValues
}