package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.BaseObservable

/**
 * Base ViewModel collects common methods and variables.
 *
 * @author  jieyi
 * @since   5/8/17
 */
abstract class BaseViewModel: BaseObservable(), IViewModel {
    // OPTIMIZE(jieyi): 9/29/17 We might make a general callback listener object!?
    // Ex: callbackListener: HashMap<String, (parameters) -> Unit>
    override fun onAttach() {}

    override fun onDetach() {}
}