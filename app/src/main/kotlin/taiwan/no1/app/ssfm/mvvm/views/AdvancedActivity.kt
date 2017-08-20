package taiwan.no1.app.ssfm.mvvm.views

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import taiwan.no1.app.ssfm.mvvm.viewmodels.IViewModel

/**
 * Advanced [BaseActivity] with [IViewModel] and [ViewDataBinding] for basic data binding setting in advance.
 *
 * @author  jieyi
 * @since   5/10/17
 */
abstract class AdvancedActivity<VM: IViewModel, out B: ViewDataBinding>: BaseActivity() {
    protected abstract var viewModel: VM
    protected val binding: B by lazy {
        val (activity, layoutId) = this.provideBindingLayoutId()
        // DataBinding process.
        DataBindingUtil.setContentView<B>(activity, layoutId)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    abstract protected fun provideBindingLayoutId(): Pair<Activity, Int>
}
