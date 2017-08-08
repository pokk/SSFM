package taiwan.no1.app.ssfm.mvvm.views

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import taiwan.no1.app.ssfm.mvvm.viewmodels.IViewModel

/**
 * Advanced [BaseActivity] with [IViewModel] and [ViewDataBinding] for basic setting in advance.
 *
 * @author  jieyi
 * @since   5/10/17
 */
abstract class AdvancedActivity<out VM: IViewModel, out B: ViewDataBinding>: BaseActivity() {
    protected val binding: B by lazy {
        val (activity, layoutId) = this.provideBindingLayoutId()
        DataBindingUtil.setContentView<B>(activity, layoutId)
    }
    protected val viewModel: VM by lazy { this.provideViewModel() }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    // TODO: 5/11/17 It might be using reflection to do onBind().
    abstract protected fun bind(): Unit

    abstract protected fun unbind(): Unit

    abstract protected fun provideBindingLayoutId(): Pair<Activity, Int>

    abstract protected fun provideViewModel(): VM
}
