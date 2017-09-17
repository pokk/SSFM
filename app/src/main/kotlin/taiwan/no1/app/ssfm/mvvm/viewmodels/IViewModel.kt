package taiwan.no1.app.ssfm.mvvm.viewmodels

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface IViewModel {
    fun onCreate()
    fun onResume()
    fun onSetView()
    fun onStop()
    fun onDestory()
}