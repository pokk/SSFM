package taiwan.no1.app.ssfm.functions.preference

import android.content.Context
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class PreferenceViewModel(private val context: Context) : BaseViewModel() {
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_settings)) }
}