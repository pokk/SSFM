package taiwan.no1.app.ssfm.functions.base

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import java.lang.reflect.ParameterizedType

/**
 * Advanced [BaseActivity] with [IViewModel] and [ViewDataBinding] for basic data binding setting in advance.
 *
 * @author  jieyi
 * @since   5/10/17
 */
abstract class AdvancedActivity<VM : IViewModel, out B : ViewDataBinding> : BaseActivity() {
    protected abstract var viewModel: VM
    protected val binding: B by lazy {
        val (activity, layoutId) = provideBindingLayoutId()
        // DataBinding process.
        DataBindingUtil.setContentView<B>(activity, layoutId)
    }
    // Data type of the parameters.
    private val genericVMClass: Class<*>
        get() = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>

    //region Activity lifecycle
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAttach(this)
        // HACK(jieyi): 8/21/17 Using reflection here, the performance might become lower. Maybe there are some better ways to do.
        binding.javaClass.getMethod("setVm", genericVMClass).invoke(binding, viewModel)
    }

    @CallSuper
    override fun onDestroy() {
        viewModel.onDetach()
        binding.javaClass.getMethod("setVm", genericVMClass).invoke(binding, null)
        super.onDestroy()
    }
    //endregion

    abstract protected fun provideBindingLayoutId(): Pair<Activity, Int>
}
