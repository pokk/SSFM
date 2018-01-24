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
abstract class BaseUsecase<T, R : RequestValues>(protected val repository: IDataStore) {
    /** Provide a common parameter variable for the children class. */
    var parameters: R? = null
    // TODO(jieyi): 8/14/17 This two functions could be a interface.
    /** Obtain a thread for while [Observable] is doing their tasks.*/
    protected open var obtainSubscribeScheduler = Schedulers.io()
    /** Obtain a thread for while [Observable] is doing their tasks.*/
    protected open var obtainObserverScheduler: Scheduler = AndroidSchedulers.mainThread()

    //region Usecase with an anonymous function.
    /**
     * Execute the current use case.
     *
     * @param lifecycleProvider the life cycle provider for cutting RxJava runs.
     * @param block add some chain actions between [subscribeOn] and [observeOn].
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun <F> execute(lifecycleProvider: LifecycleProvider<*>? = null,
                    block: Observable<T>.() -> Observable<F>,
                    observer: Observer<F>) =
        buildUseCaseObservable(block).apply { lifecycleProvider?.let { bindToLifecycle(it) } }.subscribe(observer)

    /**
     * Execute the current use case with request [parameter].
     *
     * @param parameter the parameter for retrieving data.
     * @param lifecycleProvider the life cycle provider for cutting RxJava runs.
     * @param block add some chain actions between [subscribeOn] and [observeOn].
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun <F> execute(parameter: R,
                    lifecycleProvider: LifecycleProvider<*>? = null,
                    block: Observable<T>.() -> Observable<F>,
                    observer: Observer<F>) {
        parameters = parameter
        execute(lifecycleProvider, block, observer)
    }

    /**
     * Execute the current use case with an anonymous function.
     *
     * @param lifecycleProvider an activity or a fragment of the [LifecycleProvider] object.
     * @param block add some chain actions between [subscribeOn] and [observeOn].
     * @param observer a reaction of [ObserverPlugin] from viewmodel, the data are omitted from database or remote.
     */
    fun <F> execute(lifecycleProvider: LifecycleProvider<*>? = null,
                    block: Observable<T>.() -> Observable<F>,
                    observer: ObserverPlugin<F>.() -> Unit) =
        execute(lifecycleProvider, block, ObserverPlugin<F>().apply(observer))

    /**
     * Execute the current use case with request [parameter] with an anonymous function..
     *
     * @param parameter the parameter for retrieving data.
     * @param lifecycleProvider an activity or a fragment of the [LifecycleProvider] object.
     * @param block add some chain actions between [subscribeOn] and [observeOn].
     * @param observer a reaction of [ObserverPlugin] from viewmodel, the data are omitted from database or remote.
     */
    fun <F> execute(parameter: R,
                    lifecycleProvider: LifecycleProvider<*>? = null,
                    block: Observable<T>.() -> Observable<F>,
                    observer: ObserverPlugin<F>.() -> Unit) {
        parameters = parameter
        execute(lifecycleProvider, block, observer)
    }

    /**
     * Build an [Observable] which will be used when executing the current [BaseUseCase].
     * There is a [subscribeOn] for fetching the data from the
     * [com.cloverlab.kloveroid.repository.repositories.DataRepository] works on the new thread
     * so after [subscribeOn]'s chain function will be ran on the same thread.
     * This is for who needs transfer the thread to UI, IO, or new thread again.
     *
     * @param block add some chain actions between [subscribeOn] and [observeOn].
     * @return [Observable] for connecting with a [Observer] from the kotlin layer.
     */
    private fun <F> buildUseCaseObservable(block: (Observable<T>.() -> Observable<F>)) =
        fetchUsecase()
            .subscribeOn(obtainSubscribeScheduler)
            .run { block.invoke(this) }
            .observeOn(obtainObserverScheduler)
    //endregion

    //region Usecase without an anonymous function.
    /**
     * Execute the current use case.
     *
     * @param lifecycleProvider the life cycle provider for cutting RxJava runs.
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(lifecycleProvider: LifecycleProvider<*>? = null, observer: Observer<T>) =
        buildUseCaseObservable().apply { lifecycleProvider?.let { bindToLifecycle(it) } }.subscribe(observer)

    /**
     * Execute the current use case with request [parameter].
     *
     * @param parameter the parameter for retrieving data.
     * @param lifecycleProvider the life cycle provider for cutting RxJava runs.
     * @param observer a reaction of [Observer] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(parameter: R, lifecycleProvider: LifecycleProvider<*>? = null, observer: Observer<T>) {
        parameters = parameter
        execute(lifecycleProvider, observer)
    }

    /**
     * Execute the current use case.
     *
     * @param lifecycleProvider an activity or a fragment of the [LifecycleProvider] object.
     * @param observer a reaction of [ObserverPlugin] from viewmodel, the data are omitted from database or remote.
     */
    fun execute(lifecycleProvider: LifecycleProvider<*>? = null, observer: ObserverPlugin<T>.() -> Unit) =
        execute(lifecycleProvider, ObserverPlugin<T>().apply(observer))

    /**
     * Execute the current use case with request [parameter].
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
     * Build an [Observable] which will be used when executing the current [BaseUseCase] and run on
     * the UI thread.
     *
     * @return [Observable] for connecting with a [Observer] from the kotlin layer.
     */
    private fun buildUseCaseObservable() =
        fetchUsecase()
            .subscribeOn(obtainSubscribeScheduler)
            .observeOn(obtainObserverScheduler)
    //endregion

    /**
     * Choose a method from [IDataStore] and fit this usecase for return some data.
     *
     * @return an [Observer] for chaining on working threads.
     */
    protected abstract fun fetchUsecase(): Observable<T>

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
