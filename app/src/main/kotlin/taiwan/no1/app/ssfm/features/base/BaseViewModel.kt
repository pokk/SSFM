package taiwan.no1.app.ssfm.features.base

import android.databinding.BaseObservable
import com.trello.rxlifecycle2.LifecycleProvider

/**
 * Base ViewModel collects common methods and variables.
 *
 * @author  jieyi
 * @since   5/8/17
 */
abstract class BaseViewModel : BaseObservable(), IViewModel {
    protected lateinit var lifecycleProvider: LifecycleProvider<*>

    // OPTIMIZE(jieyi): 9/29/17 We might make a general callback listener object!?
    // Ex: callbackListener: HashMap<String, (parameters) -> Unit>
    override fun <E> onAttach(lifecycleProvider: LifecycleProvider<E>) {
        this.lifecycleProvider = lifecycleProvider
    }

    override fun onDetach() {}

//    protected fun onBottomSheetDownloadClick(view: View) {
//        hideBottomSheet(view)
//    }
//
//    protected fun onBottomSheetAddToPlaylist(view: View) {
//        hideBottomSheet(view)
//    }
//
//    protected fun onBottomSheetShare(view: View) {
//        hideBottomSheet(view)
//    }
//
//    protected fun onBottomSheetCancel(view: View) {
//        hideBottomSheet(view)
//    }
//
//    private fun hideBottomSheet(view: View) {
//        BottomSheetBehavior.from(view).state = BottomSheetBehavior.STATE_HIDDEN
//    }
}