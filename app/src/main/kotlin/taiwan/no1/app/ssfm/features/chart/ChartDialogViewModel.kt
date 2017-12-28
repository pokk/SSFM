package taiwan.no1.app.ssfm.features.chart

import android.content.Context
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.features.base.BaseViewModel

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class ChartDialogViewModel(private val context: Context) : BaseViewModel() {
    val test by lazy { ObservableField<String>("Hello World") }
}