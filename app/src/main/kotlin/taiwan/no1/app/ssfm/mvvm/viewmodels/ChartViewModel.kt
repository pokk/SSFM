package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartViewModel(private val context: Context): BaseViewModel() {
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_charts)) }
}