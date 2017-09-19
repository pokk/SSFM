package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.extension.ObservableString

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class LoginViewModel(activity: Activity): BaseViewModel(activity) {
    val title = ObservableString()

    init {
        title.set(activity.getString(R.string.menu_login))
    }
}