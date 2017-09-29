package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class LoginViewModel(private val context: Context): BaseViewModel() {
    val title = ObservableField<String>()

    init {
        title.set(context.getString(R.string.menu_login))
    }
}