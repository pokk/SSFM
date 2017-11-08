package taiwan.no1.app.ssfm.models.usecases

import com.devrapid.kotlinknifer.ObserverPlugin
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase.RequestValues

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
    // TODO(jieyi): 8/14/17 This two functions could be a interface.
    /** Obtain a thread for while [Observable] is doing their tasks.*/
    open protected var obtainSubscribeScheduler = Schedulers.io()
    /** Obtain a thread for while [Observable] is doing their tasks.*/
    open protected var obtainObserverScheduler: Scheduler = AndroidSchedulers.mainThread()

    /**
     * Executes the current use case.
     *
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(lifecycleProvider: LifecycleProvider<*>? = null, observer: Observer<T>) = lifecycleProvider?.let {
        buildUsecase().bindToLifecycle(it).subscribe(observer)
    } ?: buildUsecase().subscribe(observer)

    /**
     * Executes the current use case with request parameters.
     *
     * @param parameter the parameter for retrieving data.
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(parameter: R, lifecycleProvider: LifecycleProvider<*>? = null, observer: Observer<T>) {
        parameters = parameter
        execute(lifecycleProvider, observer)
    }

    /**
     * Executes the current use case.
     *
     * @param lifecycleProvider LifecycleProvider<*>?=null :
     */
    fun execute(lifecycleProvider: LifecycleProvider<*>? = null) = lifecycleProvider?.let {
        buildUsecase().bindToLifecycle(it)
    } ?: buildUsecase()

    /**
     * Executes the current use case with request parameters.
     *
     * @param parameter the parameter for retrieving data.
     * @param lifecycleProvider an activity or a fragment of the [LifecycleProvider] object.
     */
    fun execute(parameter: R, lifecycleProvider: LifecycleProvider<*>? = null): Observable<T> {
        parameters = parameter
        return execute(lifecycleProvider)
    }

    /**
     * Executes the current use case.
     *
     * @param lifecycleProvider an activity or a fragment of the [LifecycleProvider] object.
     * @param observer a reaction of [ObserverPlugin] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(lifecycleProvider: LifecycleProvider<*>? = null, observer: ObserverPlugin<T>.() -> Unit) =
        lifecycleProvider?.let {
            buildUsecase().bindToLifecycle(it).subscribe(ObserverPlugin<T>().apply(observer))
        } ?: buildUsecase().subscribe(ObserverPlugin<T>().apply(observer))

    /**
     * Executes the current use case with request parameters.
     *
     * @param parameter the parameter for retrieving data.
     * @param lifecycleProvider an activity or a fragment of the [LifecycleProvider] object.
     * @param observer a reaction of [ObserverPlugin] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(parameter: R, lifecycleProvider: LifecycleProvider<*>? = null, observer: ObserverPlugin<T>.() -> Unit) {
        parameters = parameter
        execute(lifecycleProvider, observer)
    }

    /**
     * Choose a method from [IDataStore] and fit this usecase for return some data.
     *
     * @return an [Observer] for chaining on working threads.
     */
    abstract protected fun fetchUsecase(): Observable<T>

    /**
     * Builds an [Observable] which will be used when executing the current [BaseUsecase].
     *
     * @return [Observable] for connecting with a [Observer].
     */
    private fun buildUsecase(): Observable<T> = fetchUsecase().
        subscribeOn(obtainSubscribeScheduler).
        observeOn(obtainObserverScheduler)

    /** Interface for wrap a data for passing to a request.*/
    interface RequestValues
}
