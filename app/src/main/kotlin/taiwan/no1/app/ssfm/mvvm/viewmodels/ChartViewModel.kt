package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartViewModel(activity: Activity): BaseViewModel(activity) {
    val title = ObservableField<String>()

    init {
        title.set(activity.getString(R.string.menu_charts))
    }
}