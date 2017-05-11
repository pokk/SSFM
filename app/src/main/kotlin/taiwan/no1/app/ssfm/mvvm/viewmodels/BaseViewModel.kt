package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.content.Context
import android.databinding.BaseObservable

/**
 * Base ViewModel collects common methods and variables.
 *
 * @author  jieyi
 * @since   5/8/17
 */
abstract class BaseViewModel(private val activity: Activity): BaseObservable(), IViewModel {
    protected val context: Context by lazy { this.activity.applicationContext }
}