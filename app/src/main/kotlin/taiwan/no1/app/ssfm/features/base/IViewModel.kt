package taiwan.no1.app.ssfm.features.base

import com.trello.rxlifecycle2.LifecycleProvider

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IViewModel {
    fun <E> onAttach(lifecycleProvider: LifecycleProvider<E>)
    fun onDetach()
}