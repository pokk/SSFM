package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.BaseObservable

/**
 * Base ViewModel collects common methods and variables.
 *
 * @author  jieyi
 * @since   5/8/17
 */
abstract class BaseViewModel: BaseObservable(), IViewModel {
    override fun onAttach() {}

    override fun onDetach() {}
}