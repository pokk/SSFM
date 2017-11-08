package taiwan.no1.app.ssfm.misc.extension

import com.devrapid.kotlinknifer.ObserverPlugin
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase.RequestValues

/**
 * @author  jieyi
 * @since   10/3/17
 */
fun <T, V: RequestValues, E> LifecycleProvider<E>.execute(usecase: BaseUsecase<T, V>,
                                                          parameter: V,
                                                          observer: ObserverPlugin<T>.() -> Unit) =
    usecase.execute(parameter, this, observer)

fun <T, V: RequestValues, E> LifecycleProvider<E>.execute(usecase: BaseUsecase<T, V>,
                                                          observer: ObserverPlugin<T>.() -> Unit) =
    usecase.execute(this, observer)
