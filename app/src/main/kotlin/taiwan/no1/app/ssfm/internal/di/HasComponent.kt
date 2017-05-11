package taiwan.no1.app.ssfm.internal.di

/**
 * Interface representing a contract for clients that contains a component for dependency injection.
 *
 * @author  jieyi
 * @since   5/11/17
 */
interface HasComponent<out C> {
    fun getFragmentComponent(): C
}