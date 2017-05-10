package taiwan.no1.app.ssfm.mvvm.ui

import android.databinding.ViewDataBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.IViewModel

/**
 * Advanced [BaseActivity] with [IViewModel] and [ViewDataBinding] for basic setting in advance.
 * @author  jieyi
 * @since   5/10/17
 */
abstract class AdvancedActivity<VM: IViewModel, B: ViewDataBinding>: BaseActivity()